package fr.diginamic.gestit_back.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static org.assertj.core.api.Assertions.*;

import fr.diginamic.gestit_back.controller.CovoiturageControllerTest;
import fr.diginamic.gestit_back.entites.Adresse;
import fr.diginamic.gestit_back.entites.Commune;
import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Marque;
import fr.diginamic.gestit_back.entites.Modele;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculePerso;
import fr.diginamic.gestit_back.repository.CovoiturageRepository;
import fr.diginamic.gestit_back.service.CovoiturageService;

import java.util.Date;

import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
public class CovoiturageServiceTests {

    CovoiturageService cobaye;

    @Autowired
    ApplicationContext context;

    private Covoiturage covoiturageExample;

    @Test
    public void testSaveShouldReturnCovoiturage() throws Exception {
        this.covoiturageExample = createCovoiturageForTest();
        this.covoiturageExample.setId(852);
        CovoiturageRepository doublureCovoiturageRepository = Mockito.mock(CovoiturageRepository.class);

        when(doublureCovoiturageRepository.save(this.covoiturageExample))
                .thenReturn(this.covoiturageExample);
        this.cobaye = new CovoiturageService(doublureCovoiturageRepository);
        Covoiturage created = this.cobaye.add(this.covoiturageExample);
        assertThat(created.getId()).isSameAs(this.covoiturageExample.getId());
        Mockito.verify(doublureCovoiturageRepository,
                times(1)).save(any(Covoiturage.class));

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
