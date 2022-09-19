package com.authenticationapplication.controller;

import com.authenticationapplication.entity.User;
import com.authenticationapplication.entity.VerificationToken;
import com.authenticationapplication.event.RegistrationEventPublisher;
import com.authenticationapplication.model.PasswordModel;
import com.authenticationapplication.model.UserModel;
import com.authenticationapplication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@Slf4j
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

    @GetMapping("/resendverificationtoken")
    public String generateNewVerificationToken(@RequestParam("token") String token, HttpServletRequest request) {
        VerificationToken verificationToken = userService.generateVerificationToken(token);
        User user = verificationToken.getUser();
        generateNewVerificationMail(user, applicationUrl(request), verificationToken);
        return "Verification link sent";
    }

    @PostMapping("/resetpassword")
    public String passwordReset(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        String url = " ";
        if (user != null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetToken(user, token);
            url = resetPasswordTokenMail(user, applicationUrl(request), token);
        }
        return url;
    }

    private String resetPasswordTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl+"/savepassword?token="+token;

        log.info("Click this link to confirm email {}", url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return  "http://"+
                request.getServerName()+
                ":"+
                request.getServerPort()+
                request.getContextPath();
    }

    public void generateNewVerificationMail(User user, String applicationurl, VerificationToken token) {
        String url = applicationurl+"/verification?token="+token.getToken();

        log.info("Click this link to confirm email {}", url);
    }

}
