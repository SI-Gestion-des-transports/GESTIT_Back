package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import fr.diginamic.gestit_back.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationVehiculeServiceRepository extends JpaRepository<ReservationVehiculeService, Integer> {

    List<ReservationVehiculeService> findReservationVehiculeServiceByCollaborateur (Utilisateur utilisateur);

    void deleteReservationVehiculeServiceByCollaborateur_IdAndAndId(Integer collabId, Integer resId);

    List<ReservationVehiculeService> findAllByVehiculeServiceId(Integer vehiculeServiceId);

    void deleteAllByVehiculeServiceId(Integer vehiculeServiceId);
}
