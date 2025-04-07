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
            @RequestBody(required = false)  UserDTO userDTO,
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
}