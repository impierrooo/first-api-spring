package com.example.firstapi;

import org.springframework.data.jpa.repository.JpaRepository;

// On dit : "Ceci est une interface qui gère des 'User' dont l'ID est de type 'Long'"
public interface UserRepository extends JpaRepository<User, Long> {
    // C'est vide ? OUI !
    // Spring Boot va générer automatiquement findAll(), save(), delete(), findById()...
}