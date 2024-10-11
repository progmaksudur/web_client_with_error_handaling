package com.spring_assignment.web_client_with_error_handaling.webclient;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class BaseWebClient {

    private final WebClient.Builder builder;
    private final String baseUrl;
    private final Map<String, String> headers = new HashMap<>();

    // Constructor with base URL
    public BaseWebClient(WebClient.Builder builder, String baseUrl) {
        this.builder = builder;
        this.baseUrl = baseUrl;
    }

    // Method to add a header
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    // Method to remove a header
    public void removeHeader(String key) {
        headers.remove(key);
    }

    // Method to clear all headers
    public void clearHeaders() {
        headers.clear();
    }

    // GET request method
    public <T> Disposable get(String endpoint, Class<T> responseType, Consumer<T> onSuccess, Consumer<Throwable> onError) {
        return webClient().get()
                .uri(endpoint)  // Use the endpoint relative to the base URL
                .retrieve()
                .onStatus(HttpStatusCode::isError, WebClientErrorHandler::handleError)  // Use WebClientErrorHandler for error handling
                .bodyToMono(responseType)
                .subscribe(onSuccess, onError);
    }

    // POST request method
    public <T, U> Disposable post(String endpoint, U requestBody, Class<T> responseType, Consumer<T> onSuccess, Consumer<Throwable> onError) {
        return webClient().post()
                .uri(endpoint)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError, WebClientErrorHandler::handleError)  // Use WebClientErrorHandler for error handling
                .bodyToMono(responseType)
                .subscribe(onSuccess, onError);
    }

    // PUT request method
    public <T, U> Disposable put(String endpoint, U requestBody, Class<T> responseType, Consumer<T> onSuccess, Consumer<Throwable> onError) {
        return webClient().put()
                .uri(endpoint)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError, WebClientErrorHandler::handleError)  // Use WebClientErrorHandler for error handling
                .bodyToMono(responseType)
                .subscribe(onSuccess, onError);
    }

    // DELETE request method
    public <T> Disposable delete(String endpoint, Class<T> responseType, Consumer<T> onSuccess, Consumer<Throwable> onError) {
        return webClient().delete()
                .uri(endpoint)
                .retrieve()
                .onStatus(HttpStatusCode::isError, WebClientErrorHandler::handleError)  // Use WebClientErrorHandler for error handling
                .bodyToMono(responseType)
                .subscribe(onSuccess, onError);
    }

    // Create and configure the WebClient instance
    private WebClient webClient() {
        WebClient client = builder.baseUrl(baseUrl).build();
        return client.mutate()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")  // Default header if needed
                .defaultHeaders(httpHeaders -> httpHeaders.setAll(headers))// Set dynamic headers
                .build();
    }
}
