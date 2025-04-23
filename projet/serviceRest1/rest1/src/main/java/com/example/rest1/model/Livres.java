package com.example.rest1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "books")
public class Livres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(name = "publication_year")
    private Integer publicationYear;

    private String genre;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Auteurs auteur;

    @Column(name = "added_by")
    private String addedBy;

}
