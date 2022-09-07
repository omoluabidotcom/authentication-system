package com.authenticationapplication.controller;

import com.authenticationapplication.entity.User;
import com.authenticationapplication.model.UserModel;
import com.authenticationapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel) {
        userService.registerNewUser(userModel);
        return "success";
    }
}
