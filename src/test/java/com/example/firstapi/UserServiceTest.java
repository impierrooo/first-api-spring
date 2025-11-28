package com.example.firstapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Active Mockito
public class UserServiceTest {

    @Mock // Crée un FAUX repository
    private UserRepository repository;

    @InjectMocks // Injecte le faux repository dans le vrai service
    private UserService service;

    @Test
    void getUserById_devrait_renvoyer_un_user_si_existe() {
        // ARRANGE (Préparation)
        User pierre = new User("Pierre", "Dev");
        // On dit au faux repo : "Si on te demande l'ID 1, renvoie Pierre"
        when(repository.findById(1L)).thenReturn(Optional.of(pierre));

        // ACT (Action)
        User resultat = service.getUserById(1L);

        // ASSERT (Vérification)
        assertEquals("Pierre", resultat.getName());
    }

    @Test
    void getUserById_devrait_planter_si_user_inexistant() {
        // ARRANGE
        // On dit au faux repo : "Si on te demande l'ID 99, renvoie VIDE"
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        // On vérifie que ça lance bien l'exception 404
        assertThrows(ResponseStatusException.class, () -> {
            service.getUserById(99L);
        });
    }

    @Test
    void updateUser_devrait_modifier_et_renvoyer_user() {
        // ARRANGE
        User existingUser = new User("Pierre", "Dev");
        User updates = new User("Pierre Updated", "Lead Dev");

        // Simulation : L'user existe
        when(repository.findById(1L)).thenReturn(Optional.of(existingUser));
        // Simulation : La sauvegarde renvoie l'objet mis à jour
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // ACT
        User result = service.updateUser(1L, updates);

        // ASSERT
        assertEquals("Lead Dev", result.getRole()); // Vérifie que le rôle a changé
        verify(repository).save(existingUser);      // Vérifie que la méthode save() a bien été appelée
    }

    @Test
    void deleteUser_devrait_appeler_le_repository_si_existe() {
        // ARRANGE
        when(repository.existsById(1L)).thenReturn(true);

        // ACT
        service.deleteUser(1L);

        // ASSERT
        verify(repository).deleteById(1L); // Vérifie que deleteById a été appelé 1 fois
    }

    @Test
    void deleteUser_devrait_planter_si_user_inexistant() {
        // ARRANGE
        when(repository.existsById(99L)).thenReturn(false);

        // ACT & ASSERT
        assertThrows(ResponseStatusException.class, () -> service.deleteUser(99L));
        // On vérifie qu'on n'a JAMAIS appelé deleteById par sécurité
        verify(repository, never()).deleteById(99L);
    }
}