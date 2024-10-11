package com.spring_assignment.web_client_with_error_handaling.config;


import com.spring_assignment.web_client_with_error_handaling.webclient.BaseWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {

    private static final String BASE_URL = "https://movies-api14.p.rapidapi.com"; // Replace with your actual base URL

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder(); // Create a WebClient builder
    }

    @Bean
    public BaseWebClient baseWebClient(WebClient.Builder builder) {
        return new BaseWebClient(builder, BASE_URL); // Pass the builder and base URL to the BaseWebClient
    }

}


