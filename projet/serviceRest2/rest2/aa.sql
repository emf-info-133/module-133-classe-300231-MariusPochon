CREATE DATABASE user;
 
USE user;
 
-- Table des utilisateurs
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('admin', 'user') NOT NULL
);
 
-- Ajout des administrateurs par défaut (mot de passe à hacher en pratique)
INSERT INTO users (username, password_hash, role) VALUES
('Siwan', 'Siwan123', 'admin'),
('Marius', 'Marius123', 'admin');