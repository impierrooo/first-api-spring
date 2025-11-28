package com.example.firstapi;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "👥 Gestion des Utilisateurs", description = "API pour créer et rechercher des employés")
public class UserController {

    private final UserService service;

    // Injection du Service
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAllUsers() {
        // On délègue au service
        return service.getAllUsers();
    }

    @GetMapping("/{id}") // L'ID est dans l'URL (ex: /users/5)
    public User getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User newUser) {
        // On délègue au service
        return service.createUser(newUser);
    }
}