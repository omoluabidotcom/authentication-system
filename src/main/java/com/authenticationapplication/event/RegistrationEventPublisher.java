package com.authenticationapplication.event;

import com.authenticationapplication.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;

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


    public String getApplicationUrl(HttpServletRequest request) {

        return  "http://"+
                request.getServerName()+
                ":"+
                request.getServerPort()+
                request.getContextPath();
    }
}
