package fr.diginamic.gestit_back.unitTests.services;

import fr.diginamic.gestit_back.dto.ReservationVehiculeServiceDto;
import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculeService;
import fr.diginamic.gestit_back.repository.ReservationVehiculeServiceRepository;
import fr.diginamic.gestit_back.service.ReservationVehiculeServiceService;
import fr.diginamic.gestit_back.service.UtilisateurService;
import fr.diginamic.gestit_back.service.VehiculeServiceService;
import fr.diginamic.gestit_back.exceptions.NotFoundOrValidException;
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

    /*
    * Test pour le listage des réservations
     */
    // Vérification que la liste est bien fournie si il y a des données en base
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

    /*
    * Tests : création d'une réservation
    */

    // Vérification qu'en l'absence d'overlapping, l'insertion est possible.
    @Test
    public void test_CreerReservationVehiculeService_NoOverlap() {

        // Arrange
        Integer userId = 1;
        ReservationVehiculeServiceDto dto = mock(ReservationVehiculeServiceDto.class);
        //Optional<List<ReservationVehiculeService>> reservationVehiculeServices = null;
        when(reservationVehiculeServiceRepository.findOverlappingReservations(any(), any(), any()))
                .thenReturn(Optional.of(Collections.emptyList()));
        when(utilisateurService.rechercheParId(userId)).thenReturn(Optional.of(new Utilisateur()));
        when(vehiculeServiceService.trouverParId(any())).thenReturn(new VehiculeService());

        // Act
        reservationVehiculeServiceService.creerReservationVehiculeService(userId, dto);

        // Assert
        verify(reservationVehiculeServiceRepository, times(1)).save(any(ReservationVehiculeService.class));
    }

    // Vérification du succès d'une insertion
    @Test
    public void test_CreerReservationVehiculeService_SuccessfulInsertion() {
        // Arrange
        Integer userId = 1;
        ReservationVehiculeServiceDto dto = mock(ReservationVehiculeServiceDto.class);
        when(reservationVehiculeServiceRepository.findOverlappingReservations(any(), any(), any()))
                .thenReturn(Optional.of(Collections.emptyList()));
        when(utilisateurService.rechercheParId(userId)).thenReturn(Optional.of(new Utilisateur()));
        when(vehiculeServiceService.trouverParId(any())).thenReturn(new VehiculeService());

        // Act
        reservationVehiculeServiceService.creerReservationVehiculeService(userId, dto);

        // Assert
        verify(reservationVehiculeServiceRepository, times(1)).save(any(ReservationVehiculeService.class));
    }

    // Vérification qu'un overlapping entre les dates de réservations et celles présentes en base jette une exception
    @Test
    public void test_CreerReservationVehiculeService_WithOverlap() {

        // Arrange
        Integer userId = 1;
        ReservationVehiculeServiceDto dto = mock(ReservationVehiculeServiceDto.class);
        List<ReservationVehiculeService> overlappingReservations = Arrays.asList(new ReservationVehiculeService());
        when(reservationVehiculeServiceRepository.findOverlappingReservations(any(), any(), any()))
                .thenReturn(Optional.of(overlappingReservations));
        when(utilisateurService.rechercheParId(userId)).thenReturn(Optional.of(new Utilisateur()));
        when(vehiculeServiceService.trouverParId(any())).thenReturn(new VehiculeService());

        // Assert
        assertThrows(NotFoundOrValidException.class, () -> {
            // Act
            reservationVehiculeServiceService.creerReservationVehiculeService(userId, dto);});
    }

    // Vérification qu'une exception empêche l'insertion en base
    @Test
    public void test_CreerReservationVehiculeService_FailedInsertion() {
        // Arrange
        Integer userId = 1;
        ReservationVehiculeServiceDto dto = mock(ReservationVehiculeServiceDto.class);
        List<ReservationVehiculeService> overlappingReservations = Collections.singletonList(new ReservationVehiculeService());
        when(reservationVehiculeServiceRepository.findOverlappingReservations(any(), any(), any()))
                .thenReturn(Optional.of(overlappingReservations));
        when(utilisateurService.rechercheParId(userId)).thenReturn(Optional.of(new Utilisateur()));
        when(vehiculeServiceService.trouverParId(any())).thenReturn(new VehiculeService());


        // Act
        assertThrows(NotFoundOrValidException.class, () -> {
            reservationVehiculeServiceService.creerReservationVehiculeService(userId, dto);
        });

        // Assert
        verify(reservationVehiculeServiceRepository, never()).save(any(ReservationVehiculeService.class));
    }


    // Test de création d'une réservation si l'utilisateur n'existe pas
    @Test
    public void test_CreerReservationVehiculeService_EmptyUser() {
        // Arrange
        Integer userId = 1;
        ReservationVehiculeServiceDto dto = mock(ReservationVehiculeServiceDto.class);
        when(utilisateurService.rechercheParId(userId))
                .thenReturn(Optional.empty());

        // Assert
        assertThrows(NotFoundOrValidException.class, () -> {
            // Act
            reservationVehiculeServiceService.creerReservationVehiculeService(userId, dto);
        });
    }

}
