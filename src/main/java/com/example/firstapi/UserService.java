package com.example.firstapi;

import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service // <--- TRES IMPORTANT : Dit à Spring "C'est ici qu'est la logique"
public class UserService {

    private final UserRepository repository;

    // On injecte le Repository ici (Le Chef a besoin d'accès au garde-manger)
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        // C'est ici qu'on pourra ajouter de la logique plus tard
        return repository.findAll();
    }

    public User createUser(User user) {
        // C'est ici qu'on pourra valider les données plus tard
        return repository.save(user);
    }

    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable avec l'ID : " + id));
    }

    public User updateUser(Long id, User userDetails) {
        // On réutilise notre méthode qui gère déjà l'erreur 404 !
        User existingUser = this.getUserById(id);

        // On met à jour les champs
        existingUser.setName(userDetails.getName());
        existingUser.setRole(userDetails.getRole());

        // On sauvegarde (save fait un UPDATE si l'ID existe déjà)
        return repository.save(existingUser);
    }

    public void deleteUser(Long id) {
        // On vérifie d'abord si l'user existe, sinon 404
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable avec l'ID : " + id);
        }
        repository.deleteById(id);
    }
}