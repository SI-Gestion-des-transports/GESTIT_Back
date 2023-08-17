package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.Adresse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AdresseRepositoryTest {

    @Autowired
    AdresseRepository adresseRepository;

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Sql("scheme.sql")
    @Sql("adresses.sql")
    void trouverAdresseParId_OK(){
        Adresse adresseExistante = adresseRepository.findById(1).orElseThrow();
        assertThat(adresseExistante.getNumero()).isEqualTo(12);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Sql("scheme.sql")
    @Sql("adresses.sql")
    void trouverAdresseParId_FALSE(){
        Optional<Adresse> adresseOptional = adresseRepository.findById(7);
        assertThat(adresseOptional).isEmpty();
    }

}