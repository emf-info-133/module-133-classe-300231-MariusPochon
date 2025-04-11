package com.example.marque_paf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class ConfigCORS {
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Autorise les requêtes de toutes les origines
        config.addAllowedOrigin("*");
        
        // Autorise tous les en-têtes
        config.addAllowedHeader("*");
        
        // Autorise toutes les méthodes (GET, POST, etc.)
        config.addAllowedMethod("*");
        
        // Autorise les cookies pour les sessions
        config.setAllowCredentials(true);
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}