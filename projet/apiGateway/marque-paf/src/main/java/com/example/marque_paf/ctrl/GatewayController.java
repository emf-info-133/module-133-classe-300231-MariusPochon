package com.example.marque_paf.ctrl;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@CrossOrigin(origins = { "http://localhost:8080" }, allowCredentials = "true") // Pour le dev local

@RestController
public class GatewayController {

    private static final String URL_REST1 = "http://localhost:8081";
    private static final String URL_REST2 = "http://service-rest2:8080/session";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GatewayController(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();

    }

    private boolean isAdmin(HttpSession session) {

        String role = (String) session.getAttribute("role");

        return role != null && role.equals("admin");

    }

        @GetMapping("/client1/getLivres")
        public ResponseEntity<String> proxyGetLivres() {
            String apiUrl = URL_REST1 + "/getLivres";
            try {
                System.out.println("🌐 Gateway → Requête envoyée à " + apiUrl);
                String response = restTemplate.getForObject(apiUrl, String.class);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                System.out.println("❌ Erreur REST1: " + e.getMessage());
                return ResponseEntity.status(500).body("Erreur appel REST1");
            }
        
    }
    
    
}
