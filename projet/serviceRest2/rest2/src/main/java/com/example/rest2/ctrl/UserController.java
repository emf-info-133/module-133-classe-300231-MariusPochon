package com.example.rest2.ctrl;

import com.example.rest2.beans.User;
import com.example.rest2.dto.UserDTO;
import com.example.rest2.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            @RequestBody(required = false) UserDTO userDTO,
            HttpSession session) {

        String finalUsername;
        String finalPassword;

        if (userDTO != null) {
            // Si données envoyées en JSON
            finalUsername = userDTO.getUsername();
            finalPassword = userDTO.getPassword();
        } else {
            // Si données envoyées en paramètres
            finalUsername = username;
            finalPassword = password;
        }

        // 2. Vérification des paramètres obligatoires
        if (finalUsername == null || finalPassword == null) {
            return ResponseEntity.badRequest().body("Username et password requis");
        }

        // 3. Recherche de l'utilisateur
        User user = userRepository.findByUsername(finalUsername).orElse(null);

        // 4. Vérification des identifiants
        if (user != null && user.getPasswordHash().equals(finalPassword)) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole().name());
            return ResponseEntity.ok("Connecté avec succès");
        }

        return ResponseEntity.status(401).body("Identifiants incorrects");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        // 1. Vérification des champs obligatoires
        if (userDTO.getUsername() == null || userDTO.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username et password requis");
        }

        // 2. Vérifier si le username existe déjà
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Nom d'utilisateur déjà utilisé");
        }

        // 3. Créer un nouvel utilisateur
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPasswordHash(userDTO.getPassword()); // A CHANGER pour du hash réel plus tard

        String role = userDTO.getRole();
        if (role == null || (!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("user"))) {
            return ResponseEntity.badRequest().body("Rôle invalide (user/admin uniquement)");
        }

        user.setRole(User.Role.valueOf(role.toLowerCase())); // Assure une correspondance propre

        userRepository.save(user);

        return ResponseEntity.ok("Utilisateur enregistré avec succès");
    }
}