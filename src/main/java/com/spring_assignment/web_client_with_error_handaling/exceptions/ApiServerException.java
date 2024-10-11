package com.spring_assignment.web_client_with_error_handaling.exceptions;

public class ApiServerException extends RuntimeException {

    public ApiServerException(String message) {
        super(message);
    }

    public ApiServerException(String message, Throwable cause) {
        super(message, cause);
    }
}