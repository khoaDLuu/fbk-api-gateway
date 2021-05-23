package com.filmbooking.api_gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {

    @Autowired
    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/users/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))

                .route("auth-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("https://myplsapp.herokuapp.com"))

                .route("booking-service", r -> r.path(
                                "/bookings/**",
                                "/tickets/**",
                                "/revenues/**")
                        .filters(f -> f.filter(filter))
                        .uri("https://fbooking-service.herokuapp.com"))

                .route("screening-service", r -> r.path(
                                "/screening/**",
                                "/date/**",
                                "/room/**")
                        .filters(f -> f.filter(filter))
                        .uri("https://app-screening-service.herokuapp.com"))

                .route("movie-service", r -> r.path("/movie/**", "/genre/**")
                        .filters(f -> f.filter(filter))
                        .uri("https://app-movie-genre-service.herokuapp.com"))

                .route(r -> r.path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://httpbin.org:80"))
                .build();
    }

}
