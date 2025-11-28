package com.example.firstapi;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;

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

    // PUT /users/{id}
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        // Note : On garde @Valid pour empêcher de mettre un nom vide lors de l'update !
        return service.updateUser(id, user);
    }

    // DELETE /users/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 No Content (Standard pour un delete réussi)
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }
}