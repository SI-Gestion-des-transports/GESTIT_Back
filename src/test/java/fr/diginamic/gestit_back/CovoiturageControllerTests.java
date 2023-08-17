package fr.diginamic.gestit_back;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
*Contenant un ensemble de static method permettant d’accéder aux assertions de
*différents éléments de la réponse (status(), header(), content(), cookie(),...)
**/
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.diginamic.gestit_back.controller.CovoiturageController;
import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.service.CovoiturageService;
import org.springframework.test.web.servlet.*;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CovoiturageController.class)
public class CovoiturageControllerTests {
    private static final String END_POINT_PATH = "/covoiturages";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CovoiturageService covoiturageService;

    @Test
    public void testAddShouldReturn400BadRequest() throws Exception {
        Covoiturage newCovoiturage = new Covoiturage(null);

        String requestBody = objectMapper.writeValueAsString(newCovoiturage);

        // La méthode perform() du MockMvc de lancer un appel de l'api avec un requête
        // HTTP
        mockMvc.perform(MockMvcRequestBuilders
                .post(END_POINT_PATH) // Spécifie la méthode HTTP
                .contentType("application/json") // Spécifie le type du contenu de la requête
                .content(requestBody)) // Place une chaine JSON dans le corps de la requête
                .andExpect(status().isBadRequest()) // Execute une assertion: n
                .andDo(print());

    }
}
