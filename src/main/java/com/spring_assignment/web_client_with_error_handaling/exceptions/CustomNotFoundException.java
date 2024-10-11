package com.spring_assignment.web_client_with_error_handaling.exceptions;

public class CustomNotFoundException extends RuntimeException {
    public CustomNotFoundException(String message) {
        super("404 NOT FOUND: " + message);
    }
}
