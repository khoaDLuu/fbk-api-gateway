package com.filmbooking.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableEurekaClient
@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public WebFluxConfigurer corsConfigurer() {
		return new WebFluxConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowCredentials(true)
						.allowedOrigins("*")
						.allowedHeaders("*")
						.allowedMethods("*")
						.exposedHeaders(HttpHeaders.SET_COOKIE);
			}
		};
	}

}
