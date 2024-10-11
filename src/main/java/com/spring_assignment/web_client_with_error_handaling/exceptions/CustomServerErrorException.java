package com.spring_assignment.web_client_with_error_handaling.exceptions;

public class CustomServerErrorException extends RuntimeException {
    public CustomServerErrorException(String message,int code) {
        super(code+"INTERNAL SERVER ERROR: " + message);
    }
}
