CREATE DATABASE library;

USE library;

-- Table des auteurs
CREATE TABLE authors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth_year INT NULL,
    nationality VARCHAR(100) NULL
);

-- Table des livres
CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    publication_year INT NULL,
    genre VARCHAR(100) NULL,
    author_id INT,
    FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE SET NULL
);