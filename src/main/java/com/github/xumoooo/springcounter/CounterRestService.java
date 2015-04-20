package com.github.xumoooo.springcounter;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CounterRestService {

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CountInformation index() {
        String userName = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        UserInformation userInformation;
        switch (userName) {
            case "admin":
                userInformation = UserInformation.ADMIN;
                break;

            case "user":
                userInformation = UserInformation.USER;
                break;

            default:
                throw new RuntimeException("unknown user '" + userName + "'");
        }

        return new CountInformation(userInformation);
    }
}
