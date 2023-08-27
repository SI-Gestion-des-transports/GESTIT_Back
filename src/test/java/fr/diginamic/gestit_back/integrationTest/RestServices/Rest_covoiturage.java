package fr.diginamic.gestit_back.integrationTest.RestServices;

import static org.mockito.Mockito.times;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;


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

@DataJpaTest
@RunWith(SpringRunner.class)
// @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class Rest_covoiturage {

    private RestTemplate restTemplate;

    private ObjectMapper convertisseurJavaJson;

    Covoiturage instanceExample;

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

    @Test
    public void firstTodo() throws URISyntaxException{
        URI uri = new URI(BASE_URL+"/1");
        Todo firstTodo = restTemplate.getForObject(uri,Todo.class);
        System.out.println(firstTodo);
        assertThat(firstTodo.getTitle()).isEqualTo("delectus aut autem");
    }
}
