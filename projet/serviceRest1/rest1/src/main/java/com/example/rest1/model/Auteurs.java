package com.example.rest1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "authors")
public class Auteurs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "birth_year")
    private Integer birthYear;

    private String nationality;

    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL)
    private List<Livres> livres;
}
