package com.example.StyleSphere.service;

import com.example.StyleSphere.exception.EmailFailureException;
import com.example.StyleSphere.models.LocalUser;
import com.example.StyleSphere.models.VerificationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${email.from}")
    private String fromAddress;

    @Value("${app.frontend.url}")
    private String url;

    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private SimpleMailMessage makeMailMessage(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);
        return simpleMailMessage;
    }

    public void SendVerificationEmail(VerificationToken verificationToken) throws EmailFailureException{
        SimpleMailMessage message = makeMailMessage();
        message.setTo(verificationToken.getUser().getEmail());
        message.setSubject("Email Verification - StyleSphere");
        message.setText("Please click the link below to verify your account. \n" + url + "/auth/verify?token=" + verificationToken.getToken());
        try{
            javaMailSender.send(message);
        }catch (MailException ex){
            throw new EmailFailureException();
        }

    }

    public void sendPasswordResetEmail(LocalUser user, String token) throws EmailFailureException{
        SimpleMailMessage message = makeMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Your Password Reset request link.");
        message.setText("You requested a password reset on our website.\n" +url+"/auth/reset?token="+ token);
        try{
            javaMailSender.send(message);
        } catch (MailException ex){
            throw new EmailFailureException();//9:50
        }
    }
}
