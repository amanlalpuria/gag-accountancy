package com.lalpuria.gag.controller;

import com.lalpuria.gag.user.User;
import com.lalpuria.gag.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

}
