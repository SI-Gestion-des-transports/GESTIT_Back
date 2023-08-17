package fr.diginamic.gestit_back;

/*Ces méthodes statiques contiennent un ensemble de méthodes statiques permettant d'accéder aux assertions de différents éléments de réponse (status(), header(), content(), cookie(),...)*/
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/*SpringBoot fournit l'annotation @WebMvcTest qui peut être utilisé pour un test de Spring MVC en faisant le focus uniquement sur les composants SpringMVC tels que les RestControllers. Lors du test, l'application charge uniquement le RestController spécifié. C'est pour cela que ce type de test nécessite l'utilisation de Mocks avec Mokito (framework de Mocks pour tests unitaires) */
@WebMvcTest(CovoiturageController.class)
public class CovoiturageControllerTests {

    private static final String END_POINT_PATH = "/covoiturages";

    /*
     * Le MockMvc permet d'executer les requêtes de confirmer la réponse retournée
     * par l'API par des assertions
     */
    @Autowired
    private MockMvc mockMvc;

    /*
     * Permet de Sérialiser un objet Java en Json et désérialiser une chaine JSOn en
     * objet Java
     */
    @Autowired
    private ObjectMapper objectMapper;

    /* Ajout d'une doublure (Mock) au contexte de l'application */
    @MockBean
    private CovoiturageService covoiturageService;

    public Covoiturage createCovoiturageForTest() {

        Commune commune = new Commune("Paris", 75000);
        Adresse adresseDepart = new Adresse(26, "rue des Alouettes", commune);
        Adresse adresseArrivee = new Adresse(32, "Bvd des Aubépines", commune);

        Utilisateur organisateur = new Utilisateur();
        organisateur.setEmail("JeanMichelDelacroix@gmail.com");
        organisateur.setMotDePasse("1234");
        organisateur.setNom("Delacroix");
        Set<Role> roleOrganisateur = new HashSet<>();
        roleOrganisateur.add(Role.COLLABORATEUR);
        organisateur.setRoles(roleOrganisateur);

        Utilisateur passager = new Utilisateur();
        passager.setEmail("RonaldMerziner@gmail.com");
        passager.setMotDePasse("4321");
        passager.setNom("Merziner");
        Set<Role> rolePassager = new HashSet<>();
        rolePassager.add(Role.COLLABORATEUR);
        passager.setRoles(rolePassager);

        VehiculePerso vehiculeOrganisateur = new VehiculePerso();
        vehiculeOrganisateur.setImmatriculation("789-hu-78");
        Modele modele = new Modele();
        Marque marque = new Marque();
        marque.setNom("Fiat");
        modele.setNom("mini500");
        modele.setMarque(marque);
        vehiculeOrganisateur.setModele(modele);
        vehiculeOrganisateur.setNombreDePlaceDisponibles(4);
        vehiculeOrganisateur.setProprietaire(organisateur);

        Set<Utilisateur> passagersABord = new HashSet<>();
        passagersABord.add(passager);

        Covoiturage covoiturage = new Covoiturage();
        covoiturage.setAdresseArrivee(adresseArrivee);
        covoiturage.setAdresseDepart(adresseDepart);
        covoiturage.setDistanceKm(15);
        covoiturage.setDureeTrajet(30);
        covoiturage.setNombrePlacesRestantes(4);
        covoiturage.setOrganisateur(organisateur);
        covoiturage.setPassagers(passagersABord);
        covoiturage.setVehiculePerso(vehiculeOrganisateur);

        return covoiturage;
    }

    @Test
    public void testAddShouldReturn400BadRequest() throws Exception {
        /*
         * Création d'un covoiturage avec une valeur null sur un attribut possedant une
         * contrainte @NotNull, pour forcer un 400 BadRequest
         */
        Covoiturage newCovoiturage = new Covoiturage(null);

        String requestBody = objectMapper.writeValueAsString(newCovoiturage);

        /*
         * La méthode perform() du MockMvc de lancer un appel de l'api avec une requête
         * HTTP.
         * Nota : Attention aux méthodes à affecter au requestBuilder ( status()),
         * print(),content()....) sont des méthodes statiques.
         * L'import doit donc comporter le mot-clé static
         */
        mockMvc.perform(MockMvcRequestBuilders
                .post(END_POINT_PATH) /* Spécifie la méthode HTTP */
                .contentType("application/json") /* Spécifie le type du contenu de la requête */
                .content(requestBody)) /* Place une chaine JSON dans le corps de la requête */
                .andExpect(status().isBadRequest()) /* Execute l'assertion */
                .andDo(print());
    }

    @Test
    public void testAddShouldReturn201Created() throws Exception {
        Covoiturage newCovoiturage = createCovoiturageForTest();

        /*
         * Pour simuler le service add, nous utilisons Mockito, en lui indiquant ce qui
         * doit rentrer dans le Mock, et ce qui doit en sortir
         */
        Mockito.when(covoiturageService.add(newCovoiturage))
                .thenReturn(newCovoiturage);

        /* Sérialisation de l'objet Java en String JSON */
        String requestBody = objectMapper.writeValueAsString(newCovoiturage);

        /*
         * Envoi de la requête pour la création d'un covoiturage, et vérification de la
         * réponse attendue (201 created)
         */
        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH).contentType("application/json")
                .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print());

        /*
         * On s'assure que la méthode add() a été appelée une fois. Ceci doit toujours
         * se faire apres l'appel de perform()
         */
        Mockito.verify(covoiturageService, times(1)).add(newCovoiturage);
    }

    @Test
    public void testGetShouldReturn404NotFound() throws Exception {
        Integer covoiturageImpossibleId = 65534;
        String requestURI = END_POINT_PATH + "/" + covoiturageImpossibleId;

        Mockito.when(covoiturageService.get(covoiturageImpossibleId)).thenThrow(CovoiturageNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
        Mockito.verify(covoiturageService, times(1)).get(covoiturageImpossibleId);
    }

    @Test
    public void testGetShouldReturn200OK() throws Exception {
        Covoiturage covoiturage = this.createCovoiturageForTest();
        covoiturage.setId(65000);
        String requestURI = END_POINT_PATH + "/" + covoiturage.getId();

        Mockito.when(covoiturageService
                .get(covoiturage.getId())).thenReturn(covoiturage);

        mockMvc.perform(MockMvcRequestBuilders.get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.dureeTrajet").value(covoiturage.getDureeTrajet()))
                .andDo(print());
    }

    @Test
    public void testListShouldReturn204NoContent() throws Exception {
        /*
         * On demande au Mock de renvoyer une arrayList vide pour générer
         * une erreur 204 noContent
         */
        Mockito.when(covoiturageService.list()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void testListShouldReturn200OK() throws Exception {
        Covoiturage covoiturage1 = this.createCovoiturageForTest();
        covoiturage1.setId(255);
        Covoiturage covoiturage2 = this.createCovoiturageForTest();
        covoiturage2.setId(256);

        List<Covoiturage> listCovoiturages = List.of(covoiturage1, covoiturage2);

        Mockito.when(covoiturageService.list()).thenReturn(listCovoiturages);

        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id").value(covoiturage1.getId()))
                .andExpect(jsonPath("$[1].id").value(covoiturage2.getId()))
                .andDo(print());
    }

    @Test
    public void testUpdateShouldReturn404NotFound() throws Exception {
        Covoiturage covoiturage = this.createCovoiturageForTest();
        covoiturage.setId(2005);
        String requestURI = END_POINT_PATH + "/" + covoiturage.getId();

        Mockito.when(covoiturageService.update(covoiturage)).thenThrow(CovoiturageNotFoundException.class);

        String requestBody = objectMapper.writeValueAsString(covoiturage);

        mockMvc.perform(MockMvcRequestBuilders.put(requestURI).contentType("application/json").content(requestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testUpdateShouldReturn400BadRequest() throws Exception {
        Covoiturage covoiturage = this.createCovoiturageForTest();
        covoiturage.setId(2005);
        /* On place une valeur null sur l'attribut contraint pour générer une erreur */
        covoiturage.setNombrePlacesRestantes(null);
        String requestURI = END_POINT_PATH + "/" + covoiturage.getId();

        String requestBody = objectMapper.writeValueAsString(covoiturage);

        mockMvc.perform(MockMvcRequestBuilders.put(requestURI).contentType("application/json").content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testUpdateShouldReturn200OK() throws Exception {
        Covoiturage covoiturage = this.createCovoiturageForTest();
        covoiturage.setId(2005);

        String requestURI = END_POINT_PATH + "/" + covoiturage.getId();

        Mockito.when(covoiturageService.update(covoiturage)).thenReturn(covoiturage);

        String requestBody = objectMapper.writeValueAsString(covoiturage);

        mockMvc.perform(MockMvcRequestBuilders.put(requestURI).contentType("application/json").content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(covoiturage.getId()))
                .andDo(print());
    }

    @Test
    public void testDeleteShouldReturn404NotFound() throws Exception {
        Covoiturage covoiturage = this.createCovoiturageForTest();
        covoiturage.setId(2005);
        String requestURI = END_POINT_PATH + "/" + covoiturage.getId();

        Mockito.doThrow(CovoiturageNotFoundException.class)
                .when(covoiturageService).delete(covoiturage.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testDeleteShouldReturn200OK() throws Exception {
        Covoiturage covoiturage = this.createCovoiturageForTest();
        covoiturage.setId(2005);
        
        String requestURI = END_POINT_PATH + "/" + covoiturage.getId();

        Mockito.doNothing().when(covoiturageService).delete(covoiturage.getId());
        
        mockMvc.perform(MockMvcRequestBuilders.delete(requestURI))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

}
