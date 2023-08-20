package fr.diginamic.gestit_back.controller;

/*Ces méthodes statiques contiennent un ensemble de méthodes statiques permettant d'accéder aux assertions de différents éléments de réponse (status(), header(), content(), cookie(),...)*/
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.print.attribute.standard.Media;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
		this.convertisseurJavaJson = JsonMapper.builder()
				.addModule(new JavaTimeModule())
				.enable(SerializationFeature.INDENT_OUTPUT)
				.build();
		this.covoiturageExample = createCovoiturageForTest();
		this.testeur = MockMvcBuilders.standaloneSetup(cobaye).build();

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
	 * On vérifie également que le service n'a été appelé qu'une seule fois
	 * 
	 * @author AtsuhikoMochizuki
	 * @throws Exception
	 */
	@Test
	public void testAdd_ShouldReturn_201Created() throws Exception {

		Mockito.when(doublureCovoiturageService.add(this.covoiturageExample))
				.thenReturn(this.covoiturageExample);

		String corpsRequete = convertisseurJavaJson.writeValueAsString(this.covoiturageExample);

		testeur.perform(post(END_POINT_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(corpsRequete))
				.andExpect(status().isCreated())
				.andDo(print());
		Mockito.verify(doublureCovoiturageService, times(1)).add(this.covoiturageExample);
	}

	/***
	 * Ce test envoie une demande d'une lecture en base de données d'un
	 * covoiturage dont l'ID est inexistant.
	 * L'objectif est de vérifier le retour du contrôleur en status
	 * 404 (NotFound).
	 * Le service normalement requis est simulé ici par une doublure Mokito.
	 * On vérifie également que le service n'a été appelé qu'une seule fois
	 * 
	 * @author AtsuhikoMochizuki
	 * @throws Exception
	 */
	@Test
	public void testGetShouldReturn404NotFound() throws Exception {
		Integer impossibleId = 65534;
		String requestURI = String.format("%s/%d", END_POINT_PATH, impossibleId);

		Mockito.when(this.doublureCovoiturageService.get(impossibleId))
				.thenThrow(CovoiturageNotFoundException.class);

		testeur.perform(get(requestURI))
				.andExpect(status().isNotFound())
				.andDo(print());
		Mockito.verify(doublureCovoiturageService, times(1)).get(impossibleId);
	}

	/***
	 * Ce test envoie une demande d'une lecture en base de données d'un
	 * covoiturage existant.
	 * L'objectif est de vérifier le retour du contrôleur en status
	 * 200 (OK).
	 * Le service normalement requis est simulé ici par une doublure Mokito.
	 * On vérifie également que le service n'a été appelé qu'une seule fois
	 * 
	 * @author AtsuhikoMochizuki
	 * @throws Exception
	 */
	@Test
	public void testGetShouldReturn200OK() throws Exception {
		this.covoiturageExample.setId(65000);
		String requestURI = String.format("%s/%d", END_POINT_PATH, this.covoiturageExample.getId());

		Mockito.when(this.doublureCovoiturageService
				.get(this.covoiturageExample.getId())).thenReturn(this.covoiturageExample);

		testeur.perform(get(requestURI))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.dureeTrajet").value(this.covoiturageExample.getDureeTrajet()))
				.andDo(print());
		Mockito.verify(this.doublureCovoiturageService, times(1)).get(this.covoiturageExample.getId());
	}

	/***
	 * Ce test envoie une demande d'une lecture en base de données de
	 * la liste des covoiturages enregistrés. La doublure renvoie une
	 * liste vide.
	 * L'objectif est de vérifier ici le retour du contrôleur en status
	 * 204 (NoContent).
	 * Le service normalement requis est simulé ici par une doublure Mokito.
	 * On vérifie également que le service n'a été appelé qu'une seule fois
	 * 
	 * @author AtsuhikoMochizuki
	 * @throws Exception
	 */
	@Test
	public void testListShouldReturn204NoContent() throws Exception {

		Mockito.when(this.doublureCovoiturageService.list()).thenReturn(new ArrayList<>());

		testeur.perform(get(END_POINT_PATH))
				.andExpect(status().isNoContent())
				.andDo(print());
		Mockito.verify(this.doublureCovoiturageService, times(1)).list();
	}

	/***
	 * Ce test crée une liste de deux covoiturages à renvoyer en réponse.
	 * L'objectif est de vérifier ici le retour du contrôleur en status
	 * 200 (OK).
	 * Le service normalement requis est simulé ici par une doublure Mokito.
	 * On vérifie également que le service n'a été appelé qu'une seule fois
	 * 
	 * @author AtsuhikoMochizuki
	 * @throws Exception
	 */
	@Test
	public void testListShouldReturn200OK() throws Exception {
		Covoiturage covoiturage1 = createCovoiturageForTest();
		covoiturage1.setId(255);
		Covoiturage covoiturage2 = createCovoiturageForTest();
		covoiturage2.setId(256);

		List<Covoiturage> listCovoiturages = List.of(covoiturage1, covoiturage2);

		Mockito.when(this.doublureCovoiturageService.list()).thenReturn(listCovoiturages);

		testeur.perform(get(END_POINT_PATH))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(covoiturage1.getId()))
				.andExpect(jsonPath("$[1].id").value(covoiturage2.getId()))
				.andDo(print());
		Mockito.verify(this.doublureCovoiturageService, times(1)).list();
	}

	/***
	 * Ce test crée une demande de modifiaction de l'id d'un
	 * covoiturage inexistant.
	 * L'objectif est de vérifier ici le retour du contrôleur en status
	 * 404 (NotFound).
	 * Le service normalement requis est simulé ici par une doublure Mokito.
	 * On vérifie également que le service n'a été appelé qu'une seule fois
	 * 
	 * @author AtsuhikoMochizuki
	 * @throws Exception
	 */
	@Test
	public void testUpdateShouldReturn404NotFound() throws Exception {
		Integer notAvailableId = 64000;
		this.covoiturageExample.setId(notAvailableId);
		String requestURI = String.format("%s/%d", END_POINT_PATH, this.covoiturageExample.getId());

		Mockito.when(this.doublureCovoiturageService.update(this.covoiturageExample))
				.thenThrow(CovoiturageNotFoundException.class);

		String requestBody = this.convertisseurJavaJson.writeValueAsString(this.covoiturageExample);

		testeur.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().isNotFound())
				.andDo(print());

		Mockito.verify(this.doublureCovoiturageService, times(1)).update(this.covoiturageExample);
	}

	/***
	 * Ce test crée une demande de modification du nombre de places restantes
	 * d'un covoiturage inexistant, à la valeur null;
	 * L'attribut étant contraint (@NotNull), une erreur doit être
	 * ainsi générée.
	 * L'objectif est de vérifier ici le retour du contrôleur en status
	 * 400 (BadRequest).
	 *
	 * @author AtsuhikoMochizuki
	 * @throws Exception
	 */
	@Test
	public void testUpdateShouldReturn400BadRequest() throws Exception {
		this.covoiturageExample.setId(2005);
		this.covoiturageExample.setNombrePlacesRestantes(null);

		String requestURI = String.format("%s/%d", END_POINT_PATH, this.covoiturageExample.getId());
		String requestBody = this.convertisseurJavaJson.writeValueAsString(this.covoiturageExample);
		testeur.perform(put(requestURI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isBadRequest())
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