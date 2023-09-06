package fr.diginamic.gestit_back.integrationTest.RestServices;

import java.util.List;
import java.io.IOException;
import java.util.Arrays;


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
public class GESTIT_API_WEB_RESTservices_Test {

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

    @Test
    public void covoiturages_afficherCovoituragesEnregistres() {
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
        ResponseEntity<List<Covoiturage>> responseEntity = restTemplate.exchange(EndPointsApp.COVOITURAGE_GET_ALL_URI,
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
    public void covoiturages_creerCovoiturage() throws IOException {

        RestTemplate restTemplate = new RestTemplate();

         Covoiturage covoiturageToSend = this.instanceExample;
        covoiturageToSend.setDistanceKm(808);
        covoiturageToSend.setDureeTrajet(605);
        covoiturageToSend.setNombrePlacesRestantes(4);

        /* Création du corps de la requête en enveloppant l'objet dans un HttpEntity */
        HttpEntity<Covoiturage> request = new HttpEntity<Covoiturage>(covoiturageToSend);

        ResponseEntity<Covoiturage> covoiturageCreatedResponse = restTemplate.exchange(
                EndPointsApp.COVOITURAGE_CREATE_URI,
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
}
