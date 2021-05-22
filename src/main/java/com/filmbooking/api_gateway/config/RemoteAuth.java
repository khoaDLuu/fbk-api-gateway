package com.filmbooking.api_gateway.config;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class RemoteAuth {
    public Mono<Boolean> isValid(String authValue) {
        WebClient apiClient = WebClient.builder().build();
        try {
            return apiClient
                .get()
                .uri(System.getenv("TOKEN_VALIDATION_URL"))
                .header("Authorization", authValue)
                .retrieve()
                .toEntity(Void.class)
                .map(resp -> Boolean.valueOf(resp.getStatusCode().is2xxSuccessful()));
        }
        catch (Exception e) {
            // ############## DEBUG ############## //
            e.printStackTrace();
            // ############## DEBUG ############## //
            return Mono.just(false);
        }
    }
}
