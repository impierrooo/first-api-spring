package com.example.firstapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class) // On ne charge QUE le controller (c'est léger)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; // L'outil pour faire des fausses requêtes

    @MockBean // On remplace le Service par un Mock (on ne teste pas le service ici)
    private UserService service;

    @Test
    void getAllUsers_devrait_renvoyer_200() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void createUser_avec_nom_vide_devrait_renvoyer_400() throws Exception {
        // On envoie un JSON invalide (nom vide)
        String jsonInvalide = "{\"name\": \"\", \"role\": \"Dev\"}";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalide))
                .andExpect(status().isBadRequest()); // On attend une erreur 400
    }

    @Test
    void createUser_valide_devrait_renvoyer_200() throws Exception {
        // On configure le mock pour qu'il renvoie un user quand on sauvegarde
        when(service.createUser(any(User.class))).thenReturn(new User("Thomas", "Dev"));

        String jsonValide = "{\"name\": \"Thomas\", \"role\": \"Dev\"}";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValide))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Thomas"));
    }

    @Test
    void updateUser_devrait_renvoyer_200_et_user_modifie() throws Exception {
        // ARRANGE
        User updatedUser = new User("Pierre", "Architecte");
        // On dit au mock : quand on update l'ID 1, renvoie l'user modifié
        when(service.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);

        // ACT & ASSERT
        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Pierre\", \"role\": \"Architecte\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("Architecte"));
    }

    @Test
    void deleteUser_devrait_renvoyer_204() throws Exception {
        // ARRANGE
        // Pour une méthode void, on dit "ne fais rien" (juste pour que ça ne plante pas)
        doNothing().when(service).deleteUser(1L);

        // ACT & ASSERT
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent()); // Vérifie le code 204
    }
}