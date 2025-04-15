package com.example.ai_code_generator_backend.controllers;

import com.example.ai_code_generator_backend.models.User;
import com.example.ai_code_generator_backend.repositories.UserRepository;
import com.example.ai_code_generator_backend.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserService userService;



    @PostMapping("/test")
    public User test() {

        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test@gmail.com");

        userService.createUser(user);


        return user;
    }



}
