package com.spring_assignment.web_client_with_error_handaling;

import com.spring_assignment.web_client_with_error_handaling.service.MovieService;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class WebClientWithErrorHandalingApplication implements CommandLineRunner {
	@Autowired
	MovieService movieService;
	public static void main(String[] args) {


		SpringApplication.run(WebClientWithErrorHandalingApplication.class, args);



	}
	///Just testing I am use this
	@Override
	public void run(String... args) throws Exception {
		// This method will run after the application context is loaded
		movieService.getData();
	}




}
