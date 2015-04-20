package com.github.xumoooo.springcounter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.io.IOException;


public class CountInformationMatcher extends BaseMatcher<CountInformation> {

    private final ObjectMapper objectMapper;

    private final String message;

    private final long count;

    CountInformationMatcher(ObjectMapper objectMapper, String message, long count) {
        this.objectMapper = objectMapper;
        this.message = message;
        this.count = count;
    }

    @Override
    public boolean matches(Object item) {
        String content = (String) item;

        CountInformation countInformation = null;
        try {
            countInformation = objectMapper.readValue(content, CountInformation.class);
        } catch (IOException e) {
            throw new RuntimeException("can't parse json", e);
        }

        return countInformation.getCount() == count && countInformation.getMessage().equals(message);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("{\"message\": \"" + message + ", \"count\":" + count + "\")");
    }
}