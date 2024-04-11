package com.lalpuria.gag.controller;

import com.lalpuria.gag.event.RegistrationCompleteEvent;
import com.lalpuria.gag.exception.UserAlreadyExistsException;
import com.lalpuria.gag.registration.RegistrationRequest;
import com.lalpuria.gag.registration.token.VerificationToken;
import com.lalpuria.gag.registration.token.VerificationTokenRespository;
import com.lalpuria.gag.user.User;
import com.lalpuria.gag.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRespository verificationTokenRespository;
    @PostMapping
    public String registerUser(@RequestBody  RegistrationRequest registrationRequest, final HttpServletRequest request){
        try{
            User user = userService.registerUser(registrationRequest);
            publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
            return "Success! check you mail to verify email";
        } catch (UserAlreadyExistsException e){
            log.error("Register user error ",e.getCause(), e.getMessage());
            return e.getMessage();
        }
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

    @GetMapping("verifyEmail")
    public String verifyEmail(@RequestParam("token") String tokenToValidate){
        VerificationToken token =  verificationTokenRespository.findByToken(tokenToValidate);
        if(token.getUser().isEnabled()){
            return "This account have already been verified!!";
        }
        return userService.validatToken(tokenToValidate);
    }
}
