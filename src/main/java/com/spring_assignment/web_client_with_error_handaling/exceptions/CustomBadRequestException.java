package com.spring_assignment.web_client_with_error_handaling.exceptions;

public class CustomBadRequestException extends RuntimeException {
    public CustomBadRequestException(String message) {
        super("400 BAD REQUEST: " + message);
    }
}
