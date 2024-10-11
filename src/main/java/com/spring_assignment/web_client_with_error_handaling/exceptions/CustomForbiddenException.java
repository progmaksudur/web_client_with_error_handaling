package com.spring_assignment.web_client_with_error_handaling.exceptions;

public class CustomForbiddenException extends RuntimeException {
    public CustomForbiddenException(String message) {
        super("403 FORBIDDEN: " + message);
    }
}
