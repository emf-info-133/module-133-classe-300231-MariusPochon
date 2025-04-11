package com.example.marque_paf.ctrl;

import com.example.marque_paf.beans.User;
import com.example.marque_paf.config.ConfigRest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {

    private final RestTemplate restTemplate;
    private final ConfigRest configRest;

    public Controller(RestTemplate restTemplate, ConfigRest configRest) {
        this.restTemplate = restTemplate;
        this.configRest = configRest;
    }

    // Gestion de l'authentification
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO, HttpSession session) {
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    configRest.getLoginPath(), 
                    userDTO, 
                    Map.class
            );
            
            if (response.getStatusCode().is2xxSuccessful()) {
                // Si la réponse contient des informations utilisateur
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null && responseBody.containsKey("userId")) {
                    // Stockage des informations dans la session
                    session.setAttribute("userId", responseBody.get("userId"));
                    session.setAttribute("username", responseBody.get("username"));
                    session.setAttribute("role", responseBody.get("role"));
                }
                return ResponseEntity.ok(responseBody);
            }
            
            return response;
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de l'authentification: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
    
    // Inscription d'un nouvel utilisateur
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        try {
            return restTemplate.postForEntity(
                    configRest.getRegisterPath(), 
                    userDTO, 
                    Object.class
            );
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de l'inscription: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
    
    // Accès à la bibliothèque (pour utilisateurs connectés)
    @GetMapping("/library/books")
    public ResponseEntity<?> getBooks(HttpSession session) {
        // Vérification de l'authentification
        if (session.getAttribute("userId") == null) {
            return ResponseEntity.status(401).body("Utilisateur non connecté");
        }
        
        try {
            return restTemplate.getForEntity(
                    configRest.getLibraryBooksPath(), 
                    Object.class
            );
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de la récupération des livres: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
    
    // Gestion des livres (pour administrateurs)
    @PostMapping("/admin/books")
    public ResponseEntity<?> addBook(@RequestBody Object bookData, HttpSession session) {
        // Vérification du rôle admin
        if (!"admin".equals(session.getAttribute("role"))) {
            return ResponseEntity.status(403).body("Accès refusé: rôle admin requis");
        }
        
        try {
            return restTemplate.postForEntity(
                    configRest.getAdminBooksPath(), 
                    bookData, 
                    Object.class
            );
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de l'ajout du livre: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
    
    // Déconnexion
    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Déconnecté avec succès");
    }
}