package com.example.rest1.Ctrl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest1.model.Auteurs;
import com.example.rest1.model.AuteursRepository;
import com.example.rest1.model.Livres;
import com.example.rest1.model.LivresRepository;

@RestController
public class Controller {

    @Autowired
    private LivresRepository livresRepository;

    @Autowired
    private AuteursRepository auteursRepository;

    // Handler pour GET
    @GetMapping("/getLivres")
    public ResponseEntity<List<Livres>> getLivres() {
        List<Livres> livres = livresRepository.findAll();
        return ResponseEntity.ok(livres);
    }

    @GetMapping("/getLivre")
    public ResponseEntity<Livres> getLivre(@RequestParam Integer id) {
        Optional<Livres> livre = livresRepository.findById(id);
        if (livre.isPresent()) {
            return ResponseEntity.ok(livre.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Handler pour POST Livre
    @PostMapping("/addLivre")
    public ResponseEntity<String> addNewLivre(
            @RequestParam String title,
            @RequestParam String genre,
            @RequestParam Integer publication_year,
            @RequestParam Integer author_id) {

        // Chercher l'auteur par ID
        Auteurs auteur = auteursRepository.findById(author_id).orElse(null);

        if (auteur == null) {
            return ResponseEntity.badRequest().body("Auteur non trouvé avec l'ID : " + author_id);
        }

        // Créer un nouvel objet livre
        Livres livre = new Livres();
        livre.setTitle(title);
        livre.setGenre(genre);
        livre.setPublicationYear(publication_year);
        livre.setAuteur(auteur);

        // Sauvegarder le livre dans la base de données
        livresRepository.save(livre);

        return ResponseEntity.ok("Livre ajouté avec succès : " + title);
    }

    @PostMapping("/addAuteur")
    public ResponseEntity<String> addNewAuteur(
            @RequestParam String name,
            @RequestParam Integer birth_year,
            @RequestParam String nationality) {

        // Créer un nouvel objet auteur
        Auteurs auteur = new Auteurs();
        auteur.setName(name);
        auteur.setBirthYear(birth_year);
        auteur.setNationality(nationality);

        // Sauvegarder l'auteur dans la base de données
        auteursRepository.save(auteur);

        return ResponseEntity.ok("Auteur ajouté avec succès : " + name);
    }

    @PostMapping("/deleteLivre")
    public ResponseEntity<String> deleteLivre(@RequestParam Integer id) {
        Optional<Livres> livre = livresRepository.findById(id);

        if (livre.isPresent()) {
            livresRepository.delete(livre.get());
            return ResponseEntity.ok("Livre avec ID " + id + " a été supprimé.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Livre avec ID " + id + " non trouvé.");
        }
    }

}
