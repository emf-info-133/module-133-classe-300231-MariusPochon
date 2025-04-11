package com.example.marque_paf.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

            // REST 1 → gestion des livres/auteurs (avec préfixe /api)
            .route("rest1", r -> r.path("/api/**")
                .uri("http://servicerest1:8080"))

            // REST 2 → gestion des users / sessions
            .route("rest2", r -> r.path("/session/**")
                .uri("http://servicerest2:8080"))

            .build();
    }
}
