package fr.diginamic.gestit_back.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

import fr.diginamic.gestit_back.entites.Adresse;
import fr.diginamic.gestit_back.entites.Commune;
import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Marque;
import fr.diginamic.gestit_back.entites.Modele;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculePerso;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CovoiturageRepositoryTests {

    @Autowired
    CovoiturageRepository covoiturageRepository;

    /***
     * A remplir
     * 
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    public void testSaveShouldThrowException() {
        Covoiturage testCovoiturage = createCovoiturageForTest();
        testCovoiturage.setNombrePlacesRestantes(null);

        assertThrowsExactly(ConstraintViolationException.class, () -> {
            Covoiturage saved = covoiturageRepository.save(testCovoiturage);
        });
    }

    /***
     * A remplir
     * 
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    public void testSaveShouldReturnCovoiturage() {
        Covoiturage testCovoiturage = createCovoiturageForTest();
        Covoiturage saved = covoiturageRepository.save(testCovoiturage);
        assertThat(saved.getDistanceKm()).isEqualTo(testCovoiturage.getDistanceKm());
    }

    /***
     * A remplir
     * 
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    public void testGetNotAvailableIdShouldReturnEmptyOptional() {
        Integer notAvailableId = 58777;
        Optional<Covoiturage> read = covoiturageRepository.findById(notAvailableId);
        assertTrue(read.isEmpty());
    }

    /***
     * a remplir
     * 
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    /*
     * Insertion d'un covoiturage possédant les attributs suivants:
     * id = 51, nombre de places restantes = 2, distance = 456 km
     */
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Sql("covoiturages.sql")
    public void testGetShouldReturnCovoiturage() throws Exception {
        Integer availableId = 51;
        Optional<Covoiturage> read = covoiturageRepository.findById(availableId);
        assertTrue(read.isPresent());
        assertThat(read.get().getNombrePlacesRestantes()).isEqualTo(2);
        assertThat(read.get().getDistanceKm()).isEqualTo(456);
    }

    /***
     * à remplir
     * 
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Sql("covoiturages.sql")
    public void testListShouldReturnList() throws Exception {
        List<Covoiturage> returnedList = covoiturageRepository.findAll();
        assertThat(returnedList).isNotEmpty();
        assertThat(returnedList.size()).isEqualTo(2);
    }

    /***
     * à remplir
     * 
     * @author AtsuhikoMochizuki
     * @throws Exception
     */
    @Test
    /*
     * Insertion d'un covoiturage possédant les attributs suivants:
     * id = 51, nombre de places restantes = 2, distance = 456 km
     */
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Sql("covoiturages.sql")
    public void testDeleteShouldSuppressCovoiturage() throws Exception {
        Optional<Covoiturage> read = covoiturageRepository.findById(51);
        assertThat(read.get().getId()).isEqualTo(51);
        covoiturageRepository.deleteById(51);
        Optional<Covoiturage> attemptToRead = covoiturageRepository.findById(51);
        assertThat(attemptToRead.isEmpty());
    }

    public static Covoiturage createCovoiturageForTest() {
        Covoiturage covoiturage = new Covoiturage();
        Commune commune = new Commune("Paris", 75000);
        Adresse adresseDepart = new Adresse(26, "rue des Alouettes", commune);
        Adresse adresseArrivee = new Adresse(32, "Bvd des Aubépines", commune);

        Utilisateur conducteur = new Utilisateur();
        LocalDate dateConducteur = LocalDate.of(2022, 4, 6);
        Set<Covoiturage> conducteurCovoituragesOrganises = new HashSet<>();
        Set<Covoiturage> conducteurCovoituragesPassagers = new HashSet<>();
        List<String> roleConducteur = new ArrayList<>();
        roleConducteur.add("COLLABORATEUR");
        conducteur.setEmail("RonaldMerziner@gmail.com");
        conducteur.setMotDePasse("4321");
        conducteur.setNom("Merziner");
        conducteur.setCovoituragesOrganises(conducteurCovoituragesOrganises);
        conducteur.setCovoituragesPassagers(conducteurCovoituragesPassagers);
        conducteur.setDateNonValide(dateConducteur);
        conducteur.setRoles(roleConducteur);

        Utilisateur passager = new Utilisateur();
        LocalDate datePassager = LocalDate.of(2020, 1, 8);
        Set<Covoiturage> covoituragesOrganises = new HashSet<>();
        Set<Covoiturage> covoituragesPassagers = new HashSet<>();
        List<String> rolePassager = new ArrayList<>();
        rolePassager.add("COLLABORATEUR");
        passager.setEmail("RonaldMerziner@gmail.com");
        passager.setMotDePasse("4321");
        passager.setNom("Merziner");
        passager.setCovoituragesOrganises(covoituragesOrganises);
        passager.setCovoituragesPassagers(covoituragesPassagers);
        passager.setDateNonValide(datePassager);
        passager.setRoles(rolePassager);
        Set<Utilisateur> passagersABord = new HashSet<>();

        /* A CORRIGER : CET APPEL POSE PROBLEME */
        // passagersABord.add(conducteur);

        VehiculePerso vehiculeConducteur = new VehiculePerso();
        vehiculeConducteur.setImmatriculation("789-hu-78");
        Modele modele = new Modele();
        Marque marque = new Marque();
        marque.setNom("Fiat");
        modele.setNom("mini500");
        modele.setMarque(marque);
        vehiculeConducteur.setModele(modele);
        vehiculeConducteur.setNombreDePlaceDisponibles(4);
        vehiculeConducteur.setProprietaire(conducteur);

        covoiturage.setDistanceKm(15);
        covoiturage.setDureeTrajet(30);
        covoiturage.setNombrePlacesRestantes(4);
        covoiturage.setAdresseDepart(adresseDepart);
        covoiturage.setAdresseArrivee(adresseArrivee);
        covoiturage.setOrganisateur(conducteur);
        covoiturage.setId(23);
        covoiturage.setVehiculePerso(vehiculeConducteur);
        covoiturage.setPassagers(passagersABord);

        return covoiturage;
    }

}
