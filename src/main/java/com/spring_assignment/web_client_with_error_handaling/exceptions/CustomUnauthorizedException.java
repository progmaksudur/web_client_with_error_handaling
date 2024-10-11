package com.spring_assignment.web_client_with_error_handaling.exceptions;

public class CustomUnauthorizedException extends RuntimeException {
    public CustomUnauthorizedException(String message) {
        super("401 UNAUTHORIZED: " + message);
    }
}
