package fr.diginamic.gestit_back.integrationTest.RestServices;

import static org.mockito.Mockito.times;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.client.RestTemplate.*;
import org.springframework.web.client.RestTemplate;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import fr.diginamic.gestit_back.configuration.JWTConfig;
import fr.diginamic.gestit_back.controller.CovoiturageController;
import fr.diginamic.gestit_back.controller.EndPointsApp;
import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.service.CovoiturageService;
import fr.diginamic.gestit_back.utils.JWTUtils;
import fr.diginamic.gestit_back.utils.RedisUtils;
import static fr.diginamic.gestit_back.unitTests.utils.TestRestServicesUtils.Covoiturage_instanceExample;
import java.util.Arrays;

import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertTrue;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;

//@DataJpaTest
// @RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class Rest_covoiturage {

    private RestTemplate restTemplate;

    private ObjectMapper convertisseurJavaJson;

    Covoiturage instanceExample;

    private static final String GET_ALL_COVOIT_URL = ("http://localhost:8080/covoiturages/getAllCovoiturages");
     public static final String BASE_URL = "https://jsonplaceholder.typicode.com/todos";
    @BeforeEach
    public void init() {

       
       
        this.convertisseurJavaJson = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();
        this.instanceExample = Covoiturage_instanceExample();
        restTemplate = new RestTemplate();
    }

    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Sql("covoiturages.sql")
    @Test
    public void allCovoiturages() {
        HttpHeaders headers = new HttpHeaders();
        /* Configuration du type de données attendues et contenues dans la requête. Ici, un objet JSON)*/
        
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        
        //Zone à remplir pour Spring Security
        //Passage du token
        //headers.add("Authorization", headerValue);

        /*Insertion des données à transmettre */
     //   HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<List<Covoiturage>> responseEntity = restTemplate.exchange(GET_ALL_COVOIT_URL,
    HttpMethod.GET,
    null,
    new ParameterizedTypeReference<List<Covoiturage>>() {});

  List<Covoiturage> covoiturages = responseEntity.getBody();
        assertThat(covoiturages.get(0).getId()).isEqualTo(601);
        assertThat(covoiturages.get(0).getNombrePlacesRestantes()).isEqualTo(3);
        assertThat(covoiturages.get(0).getId()).isEqualTo(102);

   
    }

    @Test
    public void firstTodo() throws URISyntaxException{
        URI uri = new URI(BASE_URL+"/1");
        Todo firstTodo = restTemplate.getForObject(uri,Todo.class);
        System.out.println(firstTodo);
        assertThat(firstTodo.getTitle()).isEqualTo("delectus aut autem");
    }

    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Sql("covoiturages.sql")
    @Test
    public void first() throws URISyntaxException{
        URI uri = new URI("http://localhost:8080/covoiturage/getAllCovoiturages");
        Covoiturage read = restTemplate.getForObject(uri,Covoiturage.class);
        assertThat(read).isNotNull();
        // assertThat(read.getNombrePlacesRestantes()).isEqualTo(2);
        // assertThat(read.getDistanceKm()).isEqualTo(456);
        
    }


}
