package fr.diginamic.gestit_back.integrationTest.RestServices;

import java.util.List;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
public class RestTemplateApiGESTIT {

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
    public void test_covoiturages_afficherCovoituragesEnregistres() {
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

        List<Covoiturage> covoiturages = responseEntity.getBody();
        
        assertThat(covoiturages.get(0).getId()).isEqualTo(601);
        assertThat(covoiturages.get(0).getNombrePlacesRestantes()).isEqualTo(3);
        assertThat(covoiturages.get(0).getDistanceKm()).isEqualTo(102);
    }
}
