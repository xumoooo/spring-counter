package com.github.xumoooo.springcounter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.io.IOException;


public class MessageMatcher extends BaseMatcher<Message> {

    private final ObjectMapper objectMapper;

    private final String message;

    MessageMatcher(ObjectMapper objectMapper, String message) {
        this.objectMapper = objectMapper;
        this.message = message;
    }

    @Override
    public boolean matches(Object item) {
        String content = (String) item;

        Message message = null;
        try {
            message = objectMapper.readValue(content, Message.class);
        } catch (IOException e) {
            throw new RuntimeException("can't parse json", e);
        }

        return message.getMessage().equals(this.message);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("{\"message\": \"" + message + "}");
    }
}
