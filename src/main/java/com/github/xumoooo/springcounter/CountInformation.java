package com.github.xumoooo.springcounter;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CountInformation {

    @JsonProperty
    private final String message;

    @JsonProperty
    private final long count;


    CountInformation(UserInformation userInformation) {
        this.message = userInformation.getMessage();
        this.count = userInformation.getCounter().getAndIncrement();
    }

    public String getMessage() {
        return message;
    }

    public long getCount() {
        return count;
    }
}
