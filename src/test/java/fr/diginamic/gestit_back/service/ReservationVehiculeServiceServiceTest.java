package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.ReservationVehiculeServiceDto;
import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculeService;
import fr.diginamic.gestit_back.repository.ReservationVehiculeServiceRepository;
import fr.diginamic.gestit_back.utils.NotFoundOrValidException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReservationVehiculeServiceServiceTest {

    @Mock
    private ReservationVehiculeServiceRepository reservationVehiculeServiceRepository;

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private VehiculeServiceService vehiculeServiceService;

    @InjectMocks
    private ReservationVehiculeServiceService reservationVehiculeServiceService;

    @Test
    public void test_ListeReservationVehiculeService_ReservationsVSExistantes() {
        // Arrange
        Integer userId = 1;
        Utilisateur mockUser = new Utilisateur();
        List<ReservationVehiculeService> expectedReservations = Collections.singletonList(new ReservationVehiculeService());

        when(utilisateurService.trouverParId(userId)).thenReturn(mockUser);
        when(reservationVehiculeServiceRepository.findReservationVehiculeServiceByCollaborateur(mockUser))
                .thenReturn(expectedReservations);

        // Act
        List<ReservationVehiculeService> result = reservationVehiculeServiceService.listeReservationVehiculeService(userId);

        // Assert
        assertEquals(expectedReservations, result);


    }

    @Test
    public void test_ListeReservationVehiculeService_ReservationsVSNonExistantes() {
        // Arrange
        Integer userId = 1;
        Utilisateur mockUser = new Utilisateur();

        when(utilisateurService.trouverParId(userId)).thenReturn(mockUser);
        when(reservationVehiculeServiceRepository.findReservationVehiculeServiceByCollaborateur(mockUser))
                .thenReturn(List.of());

        // Act
        List<ReservationVehiculeService> result = reservationVehiculeServiceService.listeReservationVehiculeService(userId);

        // Assert
        assertThat(result.isEmpty());
    }


    @Test
    public void test_CreerReservationVehiculeService_Successful() {
        // Arrange
        Integer userId = 1;
        ReservationVehiculeServiceDto dto = mock(ReservationVehiculeServiceDto.class);

        when(utilisateurService.trouverParId(userId)).thenReturn(new Utilisateur());
        when(vehiculeServiceService.trouverParId(any())).thenReturn(new VehiculeService());

        // Act
        reservationVehiculeServiceService.creerReservationVehiculeService(userId, dto);

        // Assert
        verify(reservationVehiculeServiceRepository, times(1)).save(any(ReservationVehiculeService.class));
    }

    @Test
    public void test_CreerReservationVehiculeService_Failed() {
        // Arrange
        Integer userId = 1;
        ReservationVehiculeServiceDto dto = mock(ReservationVehiculeServiceDto.class);

        when(utilisateurService.trouverParId(userId)).thenReturn(null);

        // Act
        assertThrows(NotFoundOrValidException.class, () -> {
            reservationVehiculeServiceService.creerReservationVehiculeService(userId, dto);
        });

        // Assert
        verify(reservationVehiculeServiceRepository, never()).save(any(ReservationVehiculeService.class));
    }

}
