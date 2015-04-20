package com.github.xumoooo.springcounter;

import java.util.concurrent.atomic.AtomicLong;


public enum UserInformation {

    ADMIN("Hail to the king!"),
    USER("Welcome user!");

    private final String message;
    private final AtomicLong counter = new AtomicLong();

    UserInformation(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public AtomicLong getCounter() {
        return counter;
    }
}
