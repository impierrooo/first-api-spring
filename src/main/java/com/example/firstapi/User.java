package com.example.firstapi;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank; // <--- Import Important
import jakarta.validation.constraints.Size;

@Entity // Dit à Spring : "Ceci est une table SQL"
@Table(name = "users") // Nom de la table dans Postgres
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément (Serial)
    private Long id;

    @NotBlank(message = "Le nom ne peut pas être vide")
    private String name;

    @Size(min = 2, max = 50, message = "Le rôle doit faire entre 2 et 50 caractères") // REGLE 2 : Taille
    @NotBlank(message = "Le rôle est obligatoire")
    private String role;

    // Constructeur vide (Obligatoire pour JPA)
    public User() {}

    // Constructeur pour nous
    public User(String name, String role) {
        this.name = name;
        this.role = role;
    }

    // Getters (Obligatoires pour que le JSON fonctionne)
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
}