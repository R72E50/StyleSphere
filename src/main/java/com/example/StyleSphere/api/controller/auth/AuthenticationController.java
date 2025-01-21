package com.example.StyleSphere.api.controller.auth;

import com.example.StyleSphere.api.models.LoginBody;
import com.example.StyleSphere.api.models.LoginResponse;
import com.example.StyleSphere.api.models.PasswordResetBody;
import com.example.StyleSphere.api.models.RegistrationBody;
import com.example.StyleSphere.exception.EmailFailureException;
import com.example.StyleSphere.exception.EmailNotFoundException;
import com.example.StyleSphere.exception.UserAlreadyExists;
import com.example.StyleSphere.exception.UserNotVerifiedException;
import com.example.StyleSphere.models.LocalUser;
import com.example.StyleSphere.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    public AuthenticationController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody){
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody)  {
        String jwt = null;
        try {
            jwt = userService.loginUser(loginBody);
        }catch (UserNotVerifiedException ex) {
            LoginResponse response = new LoginResponse();
            response.setSuccess(false);
            String reason = "USER NOT VERIFIED";
            if (ex.isNewEmailSent()) {
                reason += "_EMAIL_RESENT";
            }
            response.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }catch (EmailFailureException ex) {
            LoginResponse response = new LoginResponse();
            response.setSuccess(false);
            response.setFailureReason("EMAIL_FAILURE");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        if(jwt == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token){
        if(userService.verifyUser(token)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }



    @GetMapping("/me")
    public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user){
        return user;
    }

    @PostMapping("/forgot")
    public ResponseEntity forgotPassword(@RequestParam String email){
        try{
            userService.forgotPassword(email);
            return ResponseEntity.ok().build();
        }catch ( EmailNotFoundException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (EmailFailureException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/reset")
    public ResponseEntity resetPassword(@Valid @RequestBody PasswordResetBody body){
        userService.resetPassword(body);
        return ResponseEntity.ok().build();
    }
}
