package com.example.marque_paf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Configuration pour suivre les redirections
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        // Ajout de logs pour les requêtes/réponses
        restTemplate.getInterceptors().add((request, body, execution) -> {
            System.out.println("Envoie: " + request.getURI());
            ClientHttpResponse response = execution.execute(request, body);
            System.out.println("Réponse: " + response.getStatusCode());
            return response;
        });

        return restTemplate;
    }
}
