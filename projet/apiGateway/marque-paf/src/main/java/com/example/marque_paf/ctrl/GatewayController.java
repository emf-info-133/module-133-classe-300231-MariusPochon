package com.example.marque_paf.ctrl;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;


import com.example.marque_paf.user.UserDTO;

@CrossOrigin(origins = { "http://localhost:5501" }, allowCredentials = "true") 

@RestController
public class GatewayController {

    private static final String URL_REST1 = "http://servicerest1:8080";
    private static final String URL_REST2 = "http://servicerest2:8080/session";

    @Autowired
    private final RestTemplate restTemplate;


    public GatewayController(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
       

    }

    // PARTIE GESTION USER/ADMIN

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

    // PARTIE BIBLIOHTEQUE

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

    
    @GetMapping("/client1/getLivre")
    public ResponseEntity<String> proxyGetLivre(@RequestParam Integer id) {
        try {
            String response = restTemplate.getForObject(URL_REST1 + "/getLivre?id=" + id, String.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur appel REST1");
        }
    }


    @PostMapping("/client1/addLivre")
    public ResponseEntity<String> proxyAddLivre(
            @RequestParam String title,
            @RequestParam String genre,
            @RequestParam Integer publication_year,
            @RequestParam Integer author_id) {
        try {
            String url = URL_REST1 + "/addLivre?title=" + title +
                    "&genre=" + genre +
                    "&publication_year=" + publication_year +
                    "&author_id=" + author_id;

            String response = restTemplate.postForObject(url, null, String.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur ajout livre");
        }
    }


    @PostMapping("/client1/addAuteur")
    public ResponseEntity<String> proxyAddAuteur(
            @RequestParam String name,
            @RequestParam Integer birth_year,
            @RequestParam String nationality) {
        try {
            String url = URL_REST1 + "/addAuteur?name=" + name +
                    "&birth_year=" + birth_year +
                    "&nationality=" + nationality;

            String response = restTemplate.postForObject(url, null, String.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur ajout auteur");
        }
    }


    @DeleteMapping("/client1/deleteLivre/{id}")
    public ResponseEntity<String> proxyDeleteLivre(@PathVariable Integer id) {
        try {
            restTemplate.delete(URL_REST1 + "/deleteLivre/" + id);
            return ResponseEntity.ok("Livre supprimé");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur suppression livre");
        }
    }

    @GetMapping("/client1/getAuteurs")
    public ResponseEntity<String> proxyGetAuteurs() {
        try {
            String response = restTemplate.getForObject(URL_REST1 + "/getAuteurs", String.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur appel REST1");
        }
    }

    @GetMapping("/client1/getAuteur")
    public ResponseEntity<String> proxyGetAuteur() {
        try {
            String response = restTemplate.getForObject(URL_REST1 + "/getAuteur", String.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur appel REST1");
        }
    }

}
