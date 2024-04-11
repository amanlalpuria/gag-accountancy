package com.lalpuria.gag.controller;

import com.lalpuria.gag.controller.resource.LoginRequest;
import com.lalpuria.gag.controller.resource.LoginResult;
import com.lalpuria.gag.configuration.security.JwtHelper;
import com.lalpuria.gag.user.User;
import com.lalpuria.gag.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtHelper jwtHelper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(path = "login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginResult login(@RequestBody LoginRequest loginRequest) {

        Optional<User> userDetails;
        try {
            userDetails = userRepository.findByEmail(loginRequest.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

        if (passwordEncoder.matches(loginRequest.getPassword(), userDetails.get().getPassword())) {
            Map<String, String> claims = new HashMap<>();
            claims.put("username", userDetails.get().getFullName());

            List<GrantedAuthority> authorities = Arrays.stream(userDetails.get().getRole()
                            .split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());;
            claims.put("authorities", authorities.toString());
//            claims.put("updatedAt", userDetails.get().getUpdatedAt().toString());

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Instant.now().toEpochMilli());
            calendar.add(Calendar.DATE, 1);

            String jwt = jwtHelper.createJwtForClaims(loginRequest.getUsername(), claims, calendar.getTime());
            return new LoginResult(jwt, calendar.getTime());
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }
}
