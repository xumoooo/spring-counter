package com.github.xumoooo.springcounter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CommonRestService {

    final static String ACCESS_DENIED_MESSAGE = "Access Denied";

    final static String FORBIDDEN_MESSAGE_TEMPLATE = "User {$user} does not have access";

    @RequestMapping(value = "/401", method = RequestMethod.GET)
    public Message unauthorized() {
        return new Message(ACCESS_DENIED_MESSAGE);
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public Message accessDenied() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new Message(getForbiddenMessage(user.getUsername()));
    }

    static String getForbiddenMessage(String userName) {
        return FORBIDDEN_MESSAGE_TEMPLATE.replace("{$user}", userName);
    }
}
