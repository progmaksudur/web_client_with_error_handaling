package com.spring_assignment.web_client_with_error_handaling.webclient;

import com.spring_assignment.web_client_with_error_handaling.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.*;

public class WebClientErrorHandler {

    public static Mono<? extends Throwable> handleError(ClientResponse clientResponse) {
        HttpStatus statusCode = HttpStatus.valueOf(clientResponse.statusCode().value());

        // Check if response has a body, and handle empty body with default messages
        return clientResponse.bodyToMono(String.class)
                .defaultIfEmpty(getDefaultErrorMessage(statusCode))  // Default error message if body is empty
                .flatMap(errorMessage -> {
                    // Customize error handling based on the status code
                    if (statusCode.is4xxClientError()) {
                        return handle4xxErrors(statusCode, errorMessage);
                    } else if (statusCode.is5xxServerError()) {
                        return handle5xxErrors(statusCode, errorMessage);
                    } else {
                        return Mono.error(new WebClientResponseException(
                                errorMessage, statusCode.value(), statusCode.getReasonPhrase(),
                                clientResponse.headers().asHttpHeaders(), null, null
                        ));
                    }
                });
    }

    // Provide default error messages for specific status codes
    private static String getDefaultErrorMessage(HttpStatusCode statusCode) {
        switch (statusCode) {
            case BAD_REQUEST:
                return "Bad Request - The server could not understand the request due to invalid syntax.";
            case UNAUTHORIZED:
                return "Unauthorized - The client must authenticate itself to get the requested response.";
            case FORBIDDEN:
                return "Forbidden - The client does not have access rights to the content.";
            case NOT_FOUND:
                return "Not Found - The server can not find the requested resource.";
            case INTERNAL_SERVER_ERROR:
                return "Internal Server Error - The server has encountered a situation it doesn't know how to handle.";
            case BAD_GATEWAY:
                return "Bad Gateway - The server received an invalid response from the upstream server.";
            case SERVICE_UNAVAILABLE:
                return "Service Unavailable - The server is not ready to handle the request.";
            case GATEWAY_TIMEOUT:
                return "Gateway Timeout - The server is acting as a gateway and cannot get a response in time.";
            default:
                return "An unexpected error occurred.";
        }
    }

    // Handle 4xx errors with custom logic
    private static Mono<? extends Throwable> handle4xxErrors(HttpStatus statusCode, String errorMessage) {
        switch (statusCode) {
            case BAD_REQUEST:
                return Mono.error(new CustomBadRequestException(errorMessage));
            case UNAUTHORIZED:
                return Mono.error(new CustomUnauthorizedException(errorMessage));
            case FORBIDDEN:
                return Mono.error(new CustomForbiddenException(errorMessage));
            case NOT_FOUND:
                return Mono.error(new CustomNotFoundException(errorMessage));
            default:
                return Mono.error(new WebClientResponseException(
                        errorMessage, statusCode.value(), statusCode.getReasonPhrase(),
                        null, null, null
                ));
        }
    }

    // Handle 5xx errors with custom logic
    private static Mono<? extends Throwable> handle5xxErrors(HttpStatus statusCode, String errorMessage) {
        switch (statusCode) {
            case INTERNAL_SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE, GATEWAY_TIMEOUT:
                return Mono.error(new CustomServerErrorException(errorMessage,statusCode.value()));
            default:
                return Mono.error(new WebClientResponseException(
                        errorMessage, statusCode.value(), statusCode.getReasonPhrase(),
                        null, null, null
                ));
        }
    }
}





