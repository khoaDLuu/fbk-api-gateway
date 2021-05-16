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
                // user service
                .route("user-service", r -> r.path("/users/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))

                // auth service
                .route("auth-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))

                // booking/payment service
                .route("booking-service", r -> r.path("/bookings/**")
                        .filters(f -> f.filter(filter))
                        .uri("https://fbooking-service.herokuapp.com"))

                .route("booking-service", r -> r.path("/tickets/**")
                        .filters(f -> f.filter(filter))
                        .uri("https://fbooking-service.herokuapp.com"))

                // screening service
                .route("screening-service", r -> r.path("/screening/**")
                        .filters(f -> f.filter(filter))
                        .uri("https://app-screening-service.herokuapp.com"))

                .route("screening-service", r -> r.path("/date/**")
                        .filters(f -> f.filter(filter))
                        .uri("https://app-screening-service.herokuapp.com"))

                .route("screening-service", r -> r.path("/room/**")
                        .filters(f -> f.filter(filter))
                        .uri("https://app-screening-service.herokuapp.com"))

                // movie service
                .route(r -> r.path("/movie/**")
                        .filters(f -> f.filter(filter))
                        .uri("https://app-movie-genre-service.herokuapp.com"))

                .route("movie-service", r -> r.path("/genre/**")
                        .filters(f -> f.filter(filter))
                        .uri("https://app-movie-genre-service.herokuapp.com"))

                // test service (httpbin)
                .route(r -> r.path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://httpbin.org:80"))
                .build();
    }

}
