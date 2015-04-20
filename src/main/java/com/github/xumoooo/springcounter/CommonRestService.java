package com.github.xumoooo.springcounter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CommonRestService {

    public static final String ACCESS_DENIED_MESSAGE = "Access Denied";

    @RequestMapping(value = "/401", method = RequestMethod.GET)
    public Message unauthorized() {
        return new Message(ACCESS_DENIED_MESSAGE);
    }
}
