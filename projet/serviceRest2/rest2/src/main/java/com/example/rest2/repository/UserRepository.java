package com.example.rest2.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.example.rest2.beans.User;
// This will be AUTO IMPLEMENTED by Spring into a Bean called SkieurRepository
// CRUD refers Create, Read, Update, Delete
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);

}
