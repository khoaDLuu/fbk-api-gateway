package com.filmbooking.api_gateway;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableEurekaClient
@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	// @Bean
	// public WebFluxConfigurer corsConfigurer() {
	// 	return new WebFluxConfigurer() {
	// 		@Override
	// 		public void addCorsMappings(CorsRegistry registry) {
	// 			registry.addMapping("/**")
	// 					.allowCredentials(true)
	// 					.allowedOrigins(
	// 						"https://movie-management-app.herokuapp.com",
	// 						"https://my-movie-booking.herokuapp.com",
	// 						"http://localhost:3000"
	// 					)
	// 					.allowedHeaders("*")
	// 					.allowedMethods("OPTIONS")
	// 					.allowedMethods("POST")
	// 					.allowedMethods("GET")
	// 					.allowedMethods("PUT")
	// 					.allowedMethods("PATCH")
	// 					.allowedMethods("DELETE")
	// 					.exposedHeaders("*");
	// 		}
	// 	};
	// }

}
