package com.example.marque_paf.beans;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String username;
    private String role;
    
    public User() {}
    
    public User(Integer id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
    
    // Getters et Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
}
