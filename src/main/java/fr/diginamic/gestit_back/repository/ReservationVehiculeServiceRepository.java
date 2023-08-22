package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import fr.diginamic.gestit_back.entites.Utilisateur;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationVehiculeServiceRepository extends JpaRepository<ReservationVehiculeService, Integer> {

    List<ReservationVehiculeService> findReservationVehiculeServiceByCollaborateur(Utilisateur utilisateur);

    List<ReservationVehiculeService> findAllByVehiculeServiceIdAndAndDateHeureDepart(Integer vehiculeServiceId, LocalDateTime date);

    //ReservationVehiculeService findByVehiculeServiceId(Integer vehiculeServiceId);

    List<ReservationVehiculeService> findAllReservationsVehiculeServiceByCollaborateurAndDateHeureRetourIsBefore(Utilisateur utilisateur, LocalDateTime dateRetour);

    List<ReservationVehiculeService> findAllReservationsVehiculeServiceByCollaborateurAndDateHeureRetourIsAfter(Utilisateur utilisateur, LocalDateTime dateRetour);

    @Query("SELECT res FROM ReservationVehiculeService res WHERE res.vehiculeService.id = :vehiculeId AND " +
            "((res.dateHeureDepart < :endDate) AND (res.dateHeureRetour > :startDate))")
    Optional<List<ReservationVehiculeService>> findOverlappingReservations(@Param("vehiculeId") Integer vehiculeId,
                                                                           @Param("startDate") LocalDateTime startDate,
                                                                           @Param("endDate") LocalDateTime endDate);

    //void deleteAllByVehiculeServiceId(Integer vehiculeServiceId);

    void deleteReservationVehiculeServiceByIdAndAndDateHeureDepartIsAfterAndDateHeureRetourIsBefore(Integer vehiculeServiceId, LocalDateTime dateDepart, LocalDateTime dateRetour);

    void deleteReservationVehiculeServiceByCollaborateur_IdAndAndId(Integer collabId, Integer resId);
}
