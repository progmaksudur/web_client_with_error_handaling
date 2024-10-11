package com.spring_assignment.web_client_with_error_handaling.service;

import com.spring_assignment.web_client_with_error_handaling.webclient.BaseWebClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;


@Service
public class MovieService {

    private BaseWebClient webClient;
    @Autowired
    public MovieService(BaseWebClient webClient) {
        this.webClient = webClient;
    }


    public void getData(){
        String endUrl="/shows";
        webClient.addHeader("x-rapidapi-key", "df0b214752msh248a2d2d5755403p1c58c5jsn77649ca340e4");
        webClient.addHeader("x-rapidapi-host", "movies-api14.p.rapidapi.com");
        try {
            Disposable currentRequest = webClient.get(endUrl,String.class,this::handleSuccess,
                    throwable -> handleError(throwable.getMessage()));



        } catch (RuntimeException e) {
            System.out.println("I am here "+e.getMessage());
            throw new RuntimeException(e);
        }


    }
    private void handleSuccess(String response) {
        // Handle the successful response here
        System.out.println("Response received: " + response);
    }
    private void handleError(String errorMessage) {
        System.err.println("Error occurred: " + errorMessage);
        // Handle the error (e.g., log it, send an error response, etc.)
    }

}
