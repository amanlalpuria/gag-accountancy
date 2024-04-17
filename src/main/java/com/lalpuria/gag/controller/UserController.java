package com.lalpuria.gag.controller;

import com.lalpuria.gag.user.User;
import com.lalpuria.gag.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    @GetMapping
    public List<User> getUsers(HttpServletRequest request){
        log.debug("Received request to get users from {}", request.getRemoteAddr());;
        return userService.getUsers();
    }

}
