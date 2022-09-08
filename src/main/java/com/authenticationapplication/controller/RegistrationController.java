package com.authenticationapplication.controller;

import com.authenticationapplication.entity.User;
import com.authenticationapplication.event.RegistrationEventPublisher;
import com.authenticationapplication.model.UserModel;
import com.authenticationapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.registerNewUser(userModel);
        applicationEventPublisher.publishEvent(new RegistrationEventPublisher(user, applicationUrl(request)));
        return "success";
    }

    private String applicationUrl(HttpServletRequest request) {

        return  "http://"+
                request.getServerName()+
                ":"+
                request.getServerPort()+
                request.getContextPath();
    }

}
