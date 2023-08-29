package fr.diginamic.gestit_back.integrationTest.RestServices;

import java.util.List;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import fr.diginamic.gestit_back.controller.EndPointsApp;
import fr.diginamic.gestit_back.entites.Covoiturage;

import static fr.diginamic.gestit_back.unitTests.utils.TestRestServicesUtils.Covoiturage_instanceExample;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql("covoiturages_RestTemplateApiGestit.sql")
public class mecanismeRestTemplate {

    private RestTemplate restTemplate;
    private ObjectMapper convertisseurJavaJson;

    private Covoiturage instanceExample;

    @BeforeEach
    public void init() {

        this.convertisseurJavaJson = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();
        this.instanceExample = Covoiturage_instanceExample();
        restTemplate = new RestTemplate();
    }

    /***
     * Exécution d'une requête HTTP GET pour obtenir la réponse JSON.
     * Forme la plus simple :
     * Nous utilisons ici la forme la plus simple d'utilisation du
     * RestTemplate, qui consiste à invoquer une requête HTTP pour
     * récupérer le corps de la réponse sous la forme d'un chaine JSON
     * brute.
     * 
     * Description:
     * Ici, nous utilisons la méthode getForEntity() de la classe RestTemplate
     * pour invoquer l'API et obtenir la réponse sous la forme d'une chaîne JSON.
     * Nous devons ensuite travailler avec la réponse JSON pour extraire les
     * différents champs à l'aide de bibliothèques d'analyse JSON comme Jackson.
     * Nous préférons travailler avec des réponses JSON brutes lorsque nous ne
     * sommes intéressés que par un petit sous-ensemble d'une réponse HTTP
     * composée de nombreux champs.
     */
    @Test
    public void test_covoiturages_getAll_withGetForEntity_JsonStringResponse() {
        RestTemplate restTemplate = new RestTemplate();

        /*
         * Récupération de la réponse JSON sous la forme d'une chaîne de caractères
         * enveloppée dans une entité ResponseEntity
         */
        ResponseEntity<String> response = restTemplate.getForEntity(EndPointsApp.TEST_COVOITURAGE_GET_ALL_URI,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        /* validation du test */
        String covoiturageStr = response.getBody();
        assertThat(response.getBody()).containsAnyOf("\"id\":601");
        assertThat(response.getBody()).containsAnyOf("\"nombrePlacesRestantes\":3");
        assertThat(response.getBody()).containsAnyOf("\"distanceKm\":102");
        assertThat(response.getBody()).containsAnyOf("\"dureeTrajet\":75");
    }

    /***
     * Exécution d'une requête HTTP GET pour obtenir la réponse JSON.
     * Forme pour obtenir la réponse sous une forme de POJO.
     * 
     * Une variante de la méthode précédente consiste à obtenir la réponse
     * sous la forme d'une classe POJO.
     * Ici aussi, nous appelons la méthode getForEntity() pour recevoir
     * la réponse sous la forme d'une liste d'objets Covoiturages.
     */
    @Test
    public void test_covoiturages_getAll_withGetForEntity_responseEntity() {
        RestTemplate restTemplate = new RestTemplate();

        /* Envoi de la requete et réception sous forme d'une liste d'objets */
        ResponseEntity<List> response = restTemplate.getForEntity(EndPointsApp.TEST_COVOITURAGE_GET_ALL_URI,
                List.class);

        /*
         * Pour que Jackson connaisse le type d'élément, il faut utiliser un
         * TypeRefence.
         * Sinon, l'erreur java.util.LinkedHashMap cannot be cast to X est générée
         */
        List<Covoiturage> covoiturages = this.convertisseurJavaJson.convertValue(response.getBody(),
                new TypeReference<List<Covoiturage>>() {
                });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        /* Validation du test */
        Covoiturage readCovoiturage = covoiturages.get(1);
        assertThat(readCovoiturage.getId()).isEqualTo(601);
        assertThat(readCovoiturage.getNombrePlacesRestantes()).isEqualTo(3);
        assertThat(readCovoiturage.getDistanceKm()).isEqualTo(102);
        assertThat(readCovoiturage.getDureeTrajet()).isEqualTo(75);
    }

    /***
     * Exécution d'une requête HTTP GET pour obtenir la réponse JSON.
     * Forme pour obtenir la réponse sous une forme de POJO.
     * 
     * Une variante de la méthode précédente consiste à obtenir la réponse
     * sous la forme d'une classe POJO.
     * Ici aussi, nous appelons la méthode getForObject() pour recevoir
     * la réponse sous la forme d'une liste d'objets Covoiturages.
     * Au lieu de l'objet ResponseEntity, nous récupérons directement l'objet
     * response.
     * Bien que getForObject() semble meilleur à première vue,
     * getForEntity() renvoie des métadonnées supplémentaires importantes
     * telles que les en-têtes de la réponse et le code d'état HTTP
     * dans l'objet ResponseEntity.
     * 
     * @throws IOException
     */
    @Test
    public void test_covoiturages_getAll_withGetForObject() throws IOException {

        RestTemplate restTemplate = new RestTemplate();

        /* Envoi de la requete et réception sous forme d'une liste d'objets */
        List<?> covoiturages = restTemplate.getForObject(EndPointsApp.TEST_COVOITURAGE_GET_ALL_URI, List.class);

        /*
         * Pour que Jackson connaisse le type d'élément, il faut utiliser un
         * TypeRefence.
         * Sinon, l'erreur java.util.LinkedHashMap cannot be cast to X est générée
         */
        List<Covoiturage> covoituragesConverted = this.convertisseurJavaJson.convertValue(covoiturages,
                new TypeReference<List<Covoiturage>>() {
                });
        Covoiturage readCovoiturage = covoituragesConverted.get(1);

        /*
         * Nota : on aurait pu faire aussi de cette manière:
         * String toto =
         * this.convertisseurJavaJson.writeValueAsString(covoiturages.get(0));
         * JsonNode rootNode = this.convertisseurJavaJson.readTree(toto);
         * Covoiturage readCovoiturage =
         * this.convertisseurJavaJson.treeToValue(rootNode, Covoiturage.class);
         */

        /* Validation du test */
        assertThat(readCovoiturage.getId()).isEqualTo(601);
        assertThat(readCovoiturage.getNombrePlacesRestantes()).isEqualTo(3);
        assertThat(readCovoiturage.getDistanceKm()).isEqualTo(102);
        assertThat(readCovoiturage.getDureeTrajet()).isEqualTo(75);
    }

    /***
     * Cette fonction permet de tester le RESTservice
     * afficherCovoituragesEnregistres
     * (http://<adresse>:<port>/covoiturages/getAllCovoiturages), en envoyant la
     * requête
     * à l'API.
     * 
     * La base de données virtuelle H2 est effacé au lancement du test, puis un
     * covoiturage
     * dont certaines valeurs sont connues y sont affectées à l'aide d'un script
     * SQL.
     * L'envoi de la requête et la réception de la réponse sont gérés par le
     * RESTtemplateGestitApi,
     * qui joue le rôle de client Http.
     * 
     * La lecture et l'assertion positive des données retournées valide le test
     * 
     * @author AtsuhikoMochizuki
     */

    @Test
    public void test_covoiturages_getAll_withEchange() {
        /*
         * Création des entêtes de la requête, et configuration du type de données
         * qui y sont attendues (un objet JSON dans le cas présent)
         */
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        // Zone à remplir pour Spring Security
        // Passage du token
        // headers.add("Authorization", headerValue);

        /*
         * Envoi de la requête
         * Nota : Pour que Jackson produise une liste d'utilisateurs au lieu d'un
         * tableau,
         * il est nécessaire de décrire la liste que nous voulons créer.
         * Pour ce faire, on utilisera RestTemplate.exchange.
         * Cette méthode prend une ParameterizedTypeReference produite par une classe
         * interne anonyme.
         * Il n'est en effet pas possible d'utiliser List<User>.class
         * car Jackson n'est en mesure de déterminer le type à l'intérieur du <>, ceci
         * est
         * donc rendu possible par ParameterizedTypeReference
         * Le RestTemplate est à renseigner avec l'URI sur service REST
         */
        ResponseEntity<List<Covoiturage>> responseEntity = restTemplate.exchange(EndPointsApp.TEST_COVOITURAGE_GET_ALL_URI,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Covoiturage>>() {
                });

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Covoiturage> covoiturages = responseEntity.getBody();

        assertThat(covoiturages.get(1).getId()).isEqualTo(601);
        assertThat(covoiturages.get(1).getNombrePlacesRestantes()).isEqualTo(3);
        assertThat(covoiturages.get(1).getDistanceKm()).isEqualTo(102);
    }

    /***
     * Exécution d'une requête HTTP POST.
     * Nous invoquons une méthode HTTP POST sur une API REST avec la méthode
     * postForObject().
     * Ici, la méthode postForObject() prend le corps de la requête sous la forme
     * d'une classe HttpEntity. La HttpEntity est construite avec la classe
     * Covoiturage
     * qui est la classe POJO représentant la requête HTTP.
     * 
     */
    @Test
    public void test_covoiturages_createCovoiturage_withPostForObject() throws IOException {

        RestTemplate restTemplate = new RestTemplate();

        /* Création du corps de la requête en enveloppant l'objet dans un HttpEntity */
        Covoiturage covoiturageToSend = this.instanceExample;
        covoiturageToSend.setDistanceKm(808);
        covoiturageToSend.setDureeTrajet(605);
        covoiturageToSend.setNombrePlacesRestantes(4);

        HttpEntity<Covoiturage> request = new HttpEntity<Covoiturage>(
                covoiturageToSend);

        /* Envoi de la requête et réception de la réponse, devant être l'objet créé */
        String createdCovoiturageStr = restTemplate
                .postForObject(EndPointsApp.TEST_COVOITURAGE_CREATE_URI, request, String.class);

        JsonNode rootNode = this.convertisseurJavaJson.readTree(createdCovoiturageStr);
        Covoiturage readCovoiturage = this.convertisseurJavaJson.treeToValue(rootNode, Covoiturage.class);

        /* Validation */
        assertThat(readCovoiturage.getNombrePlacesRestantes()).isEqualTo(4);
        assertThat(readCovoiturage.getDistanceKm()).isEqualTo(808);
        assertThat(readCovoiturage.getDureeTrajet()).isEqualTo(605);
    }

    /*
     * Utilisation de exchange() pour POST
     * 
     * Dans les exemples précédents, nous avons vu des méthodes distinctes pour
     * effectuer des appels API comme postForObject() pour HTTP POST et
     * getForEntity() pour GET. La classe RestTemplate dispose de méthodes
     * similaires pour d'autres verbes HTTP tels que PUT, DELETE et PATCH.
     * 
     * La méthode exchange(), en revanche, est plus générale et peut être utilisée
     * pour différents verbes HTTP.
     * Ici, nous effectuons une requête POST en envoyant HttpMethod.POST
     * comme paramètre en plus du corps de la requête et du type de réponse POJO.
     *
     * !ATTENTION! : Ce test semble soulever un défaut de conception dans les
     * relations
     * entre entités, qui semble pouvoir se résoudre par des cascades/
     * https://www.baeldung.com/hibernate-unsaved-transient-instance-error
     */
    @Test
    public void test_covoiturages_createCovoiturage_withExchange() throws IOException {

        RestTemplate restTemplate = new RestTemplate();

         Covoiturage covoiturageToSend = this.instanceExample;
        covoiturageToSend.setDistanceKm(808);
        covoiturageToSend.setDureeTrajet(605);
        covoiturageToSend.setNombrePlacesRestantes(4);

        /* Création du corps de la requête en enveloppant l'objet dans un HttpEntity */
        HttpEntity<Covoiturage> request = new HttpEntity<Covoiturage>(covoiturageToSend);

        ResponseEntity<Covoiturage> covoiturageCreatedResponse = restTemplate.exchange(
                EndPointsApp.TEST_COVOITURAGE_CREATE_URI,
                HttpMethod.POST,
                request,
                Covoiturage.class);
        assertThat(covoiturageCreatedResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Covoiturage createdCovoiturage = covoiturageCreatedResponse.getBody();

        /* Validation */
        assertThat(createdCovoiturage.getNombrePlacesRestantes()).isEqualTo(4);
        assertThat(createdCovoiturage.getDistanceKm()).isEqualTo(808);
        assertThat(createdCovoiturage.getDureeTrajet()).isEqualTo(605);
    }

    /***
     * Cette fonction permet de tester le RESTservice
     * afficherCovoituragesEnregistres
     * (http://<adresse>:<port>/covoiturages/getAllCovoiturages), en envoyant la
     * requête
     * à l'API.
     * 
     * La base de données virtuelle H2 est effacé au lancement du test, puis un
     * covoiturage
     * dont certaines valeurs sont connues y sont affectées à l'aide d'un script
     * SQL.
     * L'envoi de la requête et la réception de la réponse sont gérés par le
     * RESTtemplateGestitApi,
     * qui joue le rôle de client Http.
     * 
     * La lecture et l'assertion positive des données retournées valide le test
     * 
     * @author AtsuhikoMochizuki
     */

    @Test
    public void createCovoiturage_withPostForEntity() {
        Covoiturage covoiturageToSend = this.instanceExample;
        covoiturageToSend.setId(502);
        covoiturageToSend.setDistanceKm(7401);
        covoiturageToSend.setDureeTrajet(4789);
        covoiturageToSend.setNombrePlacesRestantes(2);

        ResponseEntity<Covoiturage> saved = restTemplate
                .postForEntity(EndPointsApp.TEST_COVOITURAGE_CREATE_URI,
                        covoiturageToSend, Covoiturage.class);

        assertThat(saved.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(saved.getBody().getId()).isEqualTo(502);
        assertThat(saved.getBody().getDistanceKm()).isEqualTo(7401);
        assertThat(saved.getBody().getDureeTrajet()).isEqualTo(4789);
        assertThat(saved.getBody().getNombrePlacesRestantes()).isEqualTo(2);
 
        String createdCovoiturageStr = restTemplate
                .postForObject(EndPointsApp.TEST_COVOITURAGE_CREATE_URI, requete, String.class);

        /* Validation de la fonctionnalité */
        JsonNode rootNode = this.convertisseurJavaJson.readTree(createdCovoiturageStr);
        Covoiturage readCovoiturage = this.convertisseurJavaJson.treeToValue(rootNode, Covoiturage.class);

        /* Validation */
        assertThat(readCovoiturage.getNombrePlacesRestantes()).isEqualTo(18);
        assertThat(readCovoiturage.getDistanceKm()).isEqualTo(75);
        assertThat(readCovoiturage.getDureeTrajet()).isEqualTo(50);
        
    }
}
