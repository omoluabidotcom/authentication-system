package com.authenticationapplication.event;

import com.authenticationapplication.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationEventPublisher extends ApplicationEvent {

    User user;
    String regURL;

    public RegistrationEventPublisher(User user, String regURL) {
        super(user);
        this.user = user;
        this.regURL = regURL;
    }
}
