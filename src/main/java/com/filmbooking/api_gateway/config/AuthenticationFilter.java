package com.filmbooking.api_gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {

    @Autowired
    private RouterValidator routerValidator;
    @Autowired
    private RemoteAuth auth;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            if (this.isAuthMissing(request)) {
                return this.onError(
                    exchange,
                    "Authorization header is missing in request",
                    HttpStatus.UNAUTHORIZED
                );
            }

            try {
                this.getToken(request);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                return this.onError(
                    exchange,
                    "Authorization header is malformed",
                    HttpStatus.UNAUTHORIZED
                );
            }

            return auth
                .isValid(this.getAuthValue(request))
                .flatMap(tokenValid -> {
                    if (!tokenValid) {
                        return this.onError(
                            exchange,
                            "Authorization header is invalid",
                            HttpStatus.UNAUTHORIZED
                        );
                    }
                    return chain.filter(exchange);
                });
        }
        else {
            return chain.filter(exchange);
        }
    }


    /*PRIVATE*/

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthValue(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private String getToken(ServerHttpRequest request) {
        final String authValue = this.getAuthValue(request);
        final String[] parts = authValue.split(" ");
        final String type = parts[0];
        final String token = parts[1];

        // ############## DEBUG ############## //
        System.out.println("Type: " + type);
        System.out.println("Token: " + token);
        // ############## DEBUG ############## //

        return token;
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    // private void populateRequestWithPayload(ServerWebExchange exchange, String token) {
    //     // Use base64 to decode
    //     exchange.getRequest().mutate()
    //             .header("id", String.valueOf(claims.get("id")))
    //             .header("role", String.valueOf(claims.get("role")))
    //             .build();
    // }
}
