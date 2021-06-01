package com.filmbooking.api_gateway.config;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

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
                .onErrorResume(e -> Mono.error(
                    new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Your token is invalid or expired", e
                    )
                ))
                .map(resp -> Boolean.valueOf(resp.getStatusCode().is2xxSuccessful()));
        }
        catch (Exception e) {
            // ############## DEBUG ############## //
            System.out.println("[DEBUG] Exception thrown while calling API: ");
            e.printStackTrace();
            // ############## DEBUG ############## //
            return Mono.just(false);
        }
    }
}
