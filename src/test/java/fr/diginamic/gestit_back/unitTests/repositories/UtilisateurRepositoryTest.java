package fr.diginamic.gestit_back.unitTests.repositories;

import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.repository.UtilisateurRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@Slf4j
class UtilisateurRepositoryTest {
    @Autowired
    UtilisateurRepository utilisateurRepository;


    @Test
    void init() {
        log.info("UtilisateurRepository {}", utilisateurRepository);
        assertThat(utilisateurRepository).isNotNull();
    }
    @Test
    @Sql("scheme.sql")
    @Sql("utilisateurs.sql")
    void findByNom_OK() {
        // hypothèse

        // exécution du code à tester
        Utilisateur utilisateurTrouve = utilisateurRepository.findByNom("Admin1").orElseThrow();

        // vérification du résultat
        assertThat(utilisateurTrouve.getEmail()).isEqualTo("admin1@gestit.fr");
    }

    @Test
    @Sql("scheme.sql")
    @Sql("utilisateurs.sql")
    void findByNom_False() {
        // hypothèse

        // exécution du code à tester
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findByNom("Admin");

        // vérification du résultat
        assertThat(optionalUtilisateur.isEmpty());
    }


    @Test
    @Sql("scheme.sql")
    @Sql("utilisateurs.sql")
    void findByEmail_OK() {
        // hypothèse

        // exécution du code à tester
        Utilisateur utilisateurEmailTrouve = utilisateurRepository.findByEmail("admin1@gestit.fr").orElseThrow();

        // vérification du résultat
        assertThat(utilisateurEmailTrouve.getNom()).isEqualTo("Admin1");
    }

    @Test
    @Sql("scheme.sql")
    @Sql("utilisateurs.sql")
    void findByEmail_False() {
        // hypothèse

        // exécution du code à tester
        Optional<Utilisateur> optionalUtilisateurEmail = utilisateurRepository.findByEmail("XXX@gestit.fr");

        // vérification du résultat
        assertThat(optionalUtilisateurEmail.isEmpty());
    }


}