package com.example.StyleSphere.service;

import com.example.StyleSphere.api.models.LoginBody;
import com.example.StyleSphere.api.models.PasswordResetBody;
import com.example.StyleSphere.api.models.RegistrationBody;
import com.example.StyleSphere.exception.EmailFailureException;
import com.example.StyleSphere.exception.EmailNotFoundException;
import com.example.StyleSphere.exception.UserAlreadyExists;
import com.example.StyleSphere.exception.UserNotVerifiedException;
import com.example.StyleSphere.models.LocalUser;
import com.example.StyleSphere.models.VerificationToken;
import com.example.StyleSphere.models.repository.UserRepository;
import com.example.StyleSphere.models.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private UserRepository userRepository;
    private EncryptionService encryptionService;
    private JWTService jwtService;
    private EmailService emailService;
    private VerificationTokenRepository verificationTokenRepository;

    public UserService(UserRepository userRepository, EncryptionService encryptionService, JWTService jwtService, EmailService emailService, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.verificationTokenRepository = verificationTokenRepository;
    }





    @Transactional
    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExists, EmailFailureException {
        // Handles an exception throw if username/email already exists
        if(userRepository.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent() || userRepository.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()){
            throw new UserAlreadyExists();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstname(registrationBody.getFirstname());
        user.setLastname(registrationBody.getLastname());
        user.setUsername(registrationBody.getUsername());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        VerificationToken verificationToken = createVerificationToken(user);
        emailService.SendVerificationEmail(verificationToken);
        return userRepository.save(user);
    }

    private VerificationToken createVerificationToken(LocalUser user){
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);

        return verificationToken;
    }

    public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
        Optional<LocalUser> opUser = userRepository.findByUsernameIgnoreCase(loginBody.getUsername());
        if(opUser.isPresent()){
            LocalUser user = opUser.get();
            if(encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())){
                if(user.isEmailVerified()) {
                    return jwtService.generateJWT(user);
                }else{
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.size() == 0 ||
                            verificationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() - (60 * 60 -1000)));//1 hour
                    if(resend){
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenRepository.save(verificationToken);
                        emailService.SendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }

    @Transactional
    public boolean verifyUser(String token){
        Optional<VerificationToken> opToken = verificationTokenRepository.findByToken(token);
        if(opToken.isPresent()){
            VerificationToken verificationToken = opToken.get();
            LocalUser user = verificationToken.getUser();
            if(!user.isEmailVerified()){
               user.setEmailVerified(true);
                userRepository.save(user);
                verificationTokenRepository.deleteByUser(user);
                return true;
            }
        }
        return false;
    }

    public void forgotPassword(String email) throws EmailNotFoundException, EmailFailureException {
        Optional<LocalUser> opUser = userRepository.findByEmailIgnoreCase(email);
        if(opUser.isPresent()){
            LocalUser user = opUser.get();
            String token = jwtService.generatePasswordResetJWT(user);
            emailService.sendPasswordResetEmail(user, token);
        }else {
            throw new EmailNotFoundException();
        }
    }

    public  void resetPassword(PasswordResetBody body){
        String email = jwtService.getResetPasswordEmail(body.getToken());
        Optional<LocalUser> opUser = userRepository.findByEmailIgnoreCase(email);
        if (opUser.isPresent()){
            LocalUser user = opUser.get();
            user.setPassword(encryptionService.encryptPassword(body.getPassword()));
            userRepository.save(user);
        }
    }

    public boolean userHasPermissionToUser(LocalUser user, Long id){
        return  user.getId() == id;
    }
}
