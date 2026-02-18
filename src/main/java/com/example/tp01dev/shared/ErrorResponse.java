package com.example.tp01dev.shared;

import java.util.List;

public class ErrorResponse {
    private String error;
    private List<String> messages;

    public ErrorResponse(String error, List<String> messages) {
        this.error = error;
        this.messages = messages;
    }

    public String getError() {
        return error;
    }

    public List<String> getMessages() {
        return messages;
    }
}