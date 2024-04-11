package com.lalpuria.gag.event.listener;

import com.lalpuria.gag.event.RegistrationCompleteEvent;
import com.lalpuria.gag.user.User;
import com.lalpuria.gag.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;
    private final JavaMailSender emailSender;
    private  User theUser;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // 1. Get the newly register user
        theUser = event.getUser();
        // 2. Create verification token for the user
        String verificationToken = UUID.randomUUID().toString();
        // 3. Save the verification token
        userService.saveUserVerificationToken(theUser, verificationToken);
        // 4. Build the verification url to be send
        String url = event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;
        // 5. Send the email
//        sendVerificationEmail(url);
        log.info("Click the link to verify your email : {}", url);
    }

    public void sendVerificationEmail(String url){
        try {
            String subject = "Email Verification";
            String senderName = "GAG Accountancy";
            String mailContent = "<p> Hi, " + theUser.getFullName() + ", </p>" +
                    "<p>Thank you for registering with us," + "" +
                    "Please, follow the link below to complete your registration.</p>" +
                    "<a href=\"" + url + "\">Verify your email to activate your account</a>" +
                    "<p> Thank you <br> GAG Accountancy";
            MimeMessage message = emailSender.createMimeMessage();
            var messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("shabdkosh07@gmail.com", senderName);
            messageHelper.setTo(theUser.getEmail());
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContent, true);
            emailSender.send(message);
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
