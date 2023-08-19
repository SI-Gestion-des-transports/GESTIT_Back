package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.configuration.JWTConfig;
import fr.diginamic.gestit_back.dto.MessageDto;
import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import fr.diginamic.gestit_back.service.ReservationVehiculeServiceService;
import fr.diginamic.gestit_back.utils.JWTUtils;
import fr.diginamic.gestit_back.utils.NotFoundOrValidException;
import fr.diginamic.gestit_back.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(ReservationVehiculeServiceController.class)
@Import({ JWTUtils.class })
public class ReservationVehiculeServiceControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private JWTUtils jwtUtils;

    @MockBean
    private JWTConfig jwtConfig;

    @MockBean
    private RedisUtils redisUtils;

    @MockBean
    private ReservationVehiculeServiceController reservationVehiculeServiceController;

    @MockBean
    private ReservationVehiculeServiceService reservationVehiculeServiceService;

    @Test
    public void test_ListerReservations_Ok() throws Exception {
        Integer expectedUserId = 12;
        String jwtToken = "sample.jwt.token";

        Claims mockClaims = Mockito.mock(Claims.class);
        when(mockClaims.getSubject()).thenReturn(String.valueOf(expectedUserId));
        when(jwtUtils.parseJWT(jwtToken)).thenReturn(mockClaims);

        when(reservationVehiculeServiceService.listeReservationVehiculeService(expectedUserId))
                .thenReturn(List.of(new ReservationVehiculeService()));

        mockMvc = MockMvcBuilders.standaloneSetup(reservationVehiculeServiceController).build();

        mockMvc.perform(get("/reservation")
                .header("JWT-TOKEN", jwtToken)
                .header("X-XSRF-TOKEN", "4f2ed8de-4381-4e78-9eca-0d4168abb148")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /*
     * @Test
     * public void test_ListerReservations_MissingJwtToken() throws Exception {
     * mockMvc.perform(get("/reservation")
     * .header("X-XSRF-TOKEN", "4f2ed8de-4381-4e78-9eca-0d4168abb148")
     * .contentType(MediaType.APPLICATION_JSON))
     * .andExpect(status().isUnauthorized()); // assuming your controller/service
     * returns 401 when JWT is missing or invalid
     * }
     */

    /*
     * @Test
     * public void test_ListerReservations_InvalidJwtToken() throws Exception {
     * Integer expectedUserId = 12;
     * String invalidJwtToken = "invalid.jwt.token";
     * 
     * Claims mockClaims = Mockito.mock(Claims.class);
     * when(mockClaims.getSubject()).thenReturn(String.valueOf(expectedUserId));
     * when(jwtUtils.parseJWT(invalidJwtToken)).thenThrow(new
     * NotFoundOrValidException(new MessageDto("Token invalide")));
     * 
     * //when(reservationVehiculeServiceService).listeReservationVehiculeService(
     * expectedUserId);
     * 
     * mockMvc =
     * MockMvcBuilders.standaloneSetup(reservationVehiculeServiceController).build()
     * ;
     * 
     * mockMvc.perform(get("/reservation")
     * .header("JWT-TOKEN", invalidJwtToken)
     * .header("X-XSRF-TOKEN", "4f2ed8de-4381-4e78-9eca-0d4168abb148")
     * .contentType(MediaType.APPLICATION_JSON))
     * .andExpect(status().isUnauthorized());
     * }
     */

}
