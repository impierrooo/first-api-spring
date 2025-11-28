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
}