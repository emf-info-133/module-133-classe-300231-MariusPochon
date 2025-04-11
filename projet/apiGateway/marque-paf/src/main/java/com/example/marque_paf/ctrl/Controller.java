package com.example.marque_paf.ctrl;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

import java.util.Map;

import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = { "http://localhost:5500" }, allowCredentials = "true")
@RestController
public class Controller {

    private static final String URL_SESSION = "http://rest2:8080/session";
    private static final String URL_API = "http://rest1:8080/api";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public Controller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    // 🔐 ----------- SESSION (login / register / logout) -----------

    @PostMapping("/session/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials, HttpSession session) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(URL_SESSION + "/login", credentials, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, String> user = objectMapper.readValue(response.getBody(), Map.class);
                session.setAttribute("username", user.get("username"));
                return ResponseEntity.ok(response.getBody());
            }
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur login : " + e.getMessage());
        }
    }

    @PostMapping("/session/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> credentials) {
        try {
            return restTemplate.postForEntity(URL_SESSION + "/register", credentials, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur register : " + e.getMessage());
        }
    }

    @PostMapping("/session/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Déconnexion réussie");
    }

    @GetMapping("/session/user")
    public ResponseEntity<String> getUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return ResponseEntity.ok("{\"username\": \"" + username + "\"}");
        }
        return ResponseEntity.status(401).body("Aucun utilisateur connecté.");
    }

    // 📚 ----------- API (livres / auteurs) -----------

    @GetMapping("/api/getLivres")
    public ResponseEntity<String> getAllBooks() {
        try {
            return restTemplate.getForEntity(URL_API + "/books", String.class);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur récupération livres : " + e.getMessage());
        }
    }

    @GetMapping("/api/getLivre/{id}")
    public ResponseEntity<String> getBookById(@PathVariable Long id) {
        try {
            return restTemplate.getForEntity(URL_API + "/books/" + id, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur récupération livre : " + e.getMessage());
        }
    }

    @PostMapping("/api/addLivre")
    public ResponseEntity<String> addBook(@RequestBody Map<String, Object> bookData) {
        try {
            return restTemplate.postForEntity(URL_API + "/books", bookData, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur ajout livre : " + e.getMessage());
        }
    }

    @GetMapping("/api/getAuteur")
    public ResponseEntity<String> getAllAuthors() {
        try {
            return restTemplate.getForEntity(URL_API + "/authors", String.class);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur récupération auteurs : " + e.getMessage());
        }
    }

    @PostMapping("/api/addAuteur")
    public ResponseEntity<String> addAuthor(@RequestBody Map<String, Object> authorData) {
        try {
            return restTemplate.postForEntity(URL_API + "/authors", authorData, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur ajout auteur : " + e.getMessage());
        }
    }
}
