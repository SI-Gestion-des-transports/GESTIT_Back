package fr.diginamic.gestit_back;

/**
*Contenant un ensemble de static method permettant de logger
*le résultat de test (print(), log() )
**/
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
/**
*Contenant un ensemble de static method permettant d’accéder aux assertions de
*différents éléments de la réponse (status(), header(), content(), cookie(),...)
**/
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.diginamic.gestit_back.controller.CovoiturageController;
import fr.diginamic.gestit_back.service.CovoiturageService;
import org.springframework.test.web.servlet.*;

/**
*Contenant un ensemble de static method permettant de builder une requête MVC
* (get(), post(), put(),...).
**/
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CovoiturageController.class)
public class CovoiturageControllerTest {
    private static final String END_POINT_PATH = "/covoiturages";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CovoiturageService covoiturageService;

    @Test
    public void testGetShouldReturn404NotFound() throws Exception {

        String requestURI = END_POINT_PATH;

        mockMvc.perform(get(requestURI).contentType("application/json"))
                .andExpect(status().isNotFound()).andDo(print());

    }
}
