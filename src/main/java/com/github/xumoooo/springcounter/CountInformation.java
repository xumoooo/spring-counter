package com.github.xumoooo.springcounter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CountInformation {

    @JsonProperty
    private final String message;

    @JsonProperty
    private final long count;


    CountInformation(UserInformation userInformation) {
        this.message = userInformation.getMessage();
        this.count = userInformation.getCounter().incrementAndGet();
    }

    @JsonCreator
    CountInformation(@JsonProperty("message") String message, @JsonProperty("count") long count) {
        this.message = message;
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public long getCount() {
        return count;
    }
}
