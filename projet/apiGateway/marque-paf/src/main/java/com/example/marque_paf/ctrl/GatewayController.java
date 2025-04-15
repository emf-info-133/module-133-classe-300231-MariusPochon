package com.example.marque_paf.ctrl;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import org.springframework.web.util.UriComponentsBuilder;

import com.example.marque_paf.user.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@CrossOrigin(origins = { "http://localhost:8080" }, allowCredentials = "true") // Pour le dev local

@RestController
public class GatewayController {

    private static final String URL_REST1 = "http://servicerest1:8080";
    private static final String URL_REST2 = "http://servicerest2:8080/session";
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO, HttpSession session) {
        try {
            String url = URL_REST2 + "/login";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            // Copie les données de session du microservice si besoin
            if (response.getStatusCode().is2xxSuccessful()) {
                // Simuler récupération du rôle depuis le microservice (améliorable plus tard)
                session.setAttribute("username", userDTO.getUsername());
                session.setAttribute("role", "user"); // ou admin à améliorer plus tard
            }

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erreur lors de la connexion");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        try {
            String url = URL_REST2 + "/register";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'enregistrement");
        }
    }
}
