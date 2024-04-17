package com.lalpuria.gag.controller;

import com.lalpuria.gag.configuration.security.JwtUtils;
import com.lalpuria.gag.controller.resource.AuthDTO;
import com.lalpuria.gag.user.UserRegistrationDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private AuthenticationManager authenticationManager;

    @Value("${app.security.jwt.jwt_exp_time}")
    private int  JWT_EXP_TIME;

    @PostMapping(path = "login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthDTO.LoginResponse> login(@RequestBody AuthDTO.LoginRequest userLogin) {

        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                userLogin.username(),
                                userLogin.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserRegistrationDetails userDetails = (UserRegistrationDetails) authentication.getPrincipal();


        log.info("Token requested for user :{}", authentication.getAuthorities());
        Instant now = Instant.now();
        now.plus(90, ChronoUnit.MINUTES);
        String token = jwtUtils.generateToken(authentication, now, now.plus(90, ChronoUnit.MINUTES));

        AuthDTO.LoginResponse response = new AuthDTO.LoginResponse("User logged in successfully", token,now );

        return ResponseEntity.ok(response);
    }
}
