package fr.diginamic.gestit_back.unitTests.services;

import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.repository.UtilisateurRepository;
import fr.diginamic.gestit_back.service.UtilisateurService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Collections;


@SpringBootTest(classes = UtilisateurService.class)
class UtilisateurServiceTest {

    @Autowired
    UtilisateurService utilisateurService;

    @MockBean
    UtilisateurRepository utilisateurRepository;

    @Test
    void listerUtilisateurParNom_OK() {
        String nomOK ="nom1";
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nomOK);
        utilisateur.setEmail("nom1@nom.com");
        utilisateur.setMotDePasse("123");
        utilisateur.setRoles(Collections.singletonList("COLLABORATEUR"));

       // Mockito.when(utilisateurRepository.fi)
    }

    @Test
    void creerUtilisateur() {
    }

    @Test
    void modifierUtilisateur() {
    }
}