package com.example.rest1.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivresRepository extends JpaRepository<Livres, Integer> {
}
