package fr.diginamic.gestit_back.controller;

/*Ces méthodes statiques contiennent un ensemble de méthodes statiques permettant d'accéder aux assertions de différents éléments de réponse (status(), header(), content(), cookie(),...)*/
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import fr.diginamic.gestit_back.configuration.JWTConfig;
import fr.diginamic.gestit_back.controller.CovoiturageController;
import fr.diginamic.gestit_back.entites.Adresse;
import fr.diginamic.gestit_back.entites.Commune;
import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Marque;
import fr.diginamic.gestit_back.entites.Modele;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculePerso;
import fr.diginamic.gestit_back.enumerations.Role;
import fr.diginamic.gestit_back.exceptions.CovoiturageNotFoundException;
import fr.diginamic.gestit_back.service.CovoiturageService;
import fr.diginamic.gestit_back.utils.JWTUtils;
import fr.diginamic.gestit_back.utils.RedisUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Classe de tests unitaires dédiée au contrôleur REST CovoiturageController
 * de l'API.
 * Réalisés par Spring Boot l'aide de @WebMvcTest, les dépendances injectées
 * en provenance de couches différentes de celle de présentation (implémentée
 * sur le modèle MVC) sont donc simulées ici par des doublures Mokito.
 * Les requêtes et les vérification des réponses sont réalisées par un objet
 * MockMvc
 * Le processus de sérialisation/désérialisation (conversion Java<->Json) des
 * données pour le transport est réalisé par un ObjectMapper.
 * 
 * @author AtsuhikoMochizuki
 * 
 */
@WebMvcTest(CovoiturageController.class)
public class CovoiturageControllerTest {

        private static final String END_POINT_PATH = "/covoiturages";

        private MockMvc testeur;

        private ObjectMapper convertisseurJavaJson;
        private Covoiturage covoiturageExample;

        @Autowired
        private CovoiturageController cobaye;

        @MockBean
        private JWTUtils jwtUtils;
        @MockBean
        private JWTConfig jwtConfig;
        @MockBean
        private RedisUtils redisUtils;
        @MockBean
        private CovoiturageService doublureCovoiturageService;

        @BeforeEach
        public void init() {
                convertisseurJavaJson = JsonMapper.builder()
                                .addModule(new JavaTimeModule())
                                .enable(SerializationFeature.INDENT_OUTPUT)
                                .build();
                covoiturageExample = createCovoiturageForTest();

        }

        /***
         * Ce test envoie une demande de création d'un covoiturage avec un de ses
         * paramètres placés volontairement à null, alors que celui-ci possède la
         * contrainte NotNull.
         * L'objectif est de déclencher une erreur lors du traitement de la requête,
         * est de vérifier que le serveur renvoie bien un code 400 BadRequest.
         * Le service normalement appelé ici par le contrôlé est simulé par une
         * doublure Mokito.
         * 
         * @author AtsuhikoMochizuki
         * @throws Exception
         */
        @Test
        public void test_create_retourAttendu_erreur400BadRequest() throws Exception {

                this.covoiturageExample.setNombrePlacesRestantes(null);
                String corpsRequete = this.convertisseurJavaJson.writeValueAsString(this.covoiturageExample);

                this.testeur = MockMvcBuilders.standaloneSetup(cobaye).build();
                testeur.perform(post(END_POINT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(corpsRequete))
                                .andExpect(status().isBadRequest())
                                .andDo(print());
        }

        /***
         * Ce test envoie une demande de création d'un covoiturage.
         * L'objectif est de vérification le retour du contrôleur en status
         * 201 (created).
         * Le service normalement requis est simulé ici par une doublure Mokito.
         * 
         * @author AtsuhikoMochizuki
         * @throws Exception
         */
        @Test
        public void testAdd_ShouldReturn_201Created() throws Exception {
                this.convertisseurJavaJson = JsonMapper.builder()
                                .addModule(new JavaTimeModule())
                                .enable(SerializationFeature.INDENT_OUTPUT)
                                .build();

                Covoiturage covoiturageToAdd = createCovoiturageForTest();
                Mockito.when(doublureCovoiturageService.add(covoiturageToAdd))
                                .thenReturn(covoiturageToAdd);

                String corpsRequete = convertisseurJavaJson.writeValueAsString(covoiturageToAdd);

                testeur = MockMvcBuilders.standaloneSetup(cobaye).build();
                testeur.perform(post(END_POINT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(corpsRequete))
                                .andExpect(status().isCreated())
                                .andDo(print());
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