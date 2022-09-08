package com.authenticationapplication.event.listener;

import com.authenticationapplication.entity.User;
import com.authenticationapplication.event.RegistrationEventPublisher;
import com.authenticationapplication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
public class RegistrationEventListener implements ApplicationListener<RegistrationEventPublisher> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationEventPublisher event) {

        // create token for user
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationToken(user, token);

        final HttpServletRequest request = null;

        String url = event.getApplicationUrl(request)+"verification"+token;

        log.info("You click the link to confirm email", url);
    }
}
