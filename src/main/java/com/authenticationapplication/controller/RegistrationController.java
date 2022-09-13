package com.authenticationapplication.controller;

import com.authenticationapplication.entity.User;
import com.authenticationapplication.event.RegistrationEventPublisher;
import com.authenticationapplication.model.UserModel;
import com.authenticationapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/verification")
    public String verifyUser(@RequestParam("token") String token) {
        String result = userService.confirmUser(token);

        if(result.equals("Valid")) {
            return "User verified";
        }
        return "Bad User";
    }

    private String applicationUrl(HttpServletRequest request) {

        return  "http://"+
                request.getServerName()+
                ":"+
                request.getServerPort()+
                request.getContextPath();
    }

}
