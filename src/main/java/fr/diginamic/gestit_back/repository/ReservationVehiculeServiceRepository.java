package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationVehiculeServiceRepository extends JpaRepository<ReservationVehiculeService, Integer> {

    Optional<ReservationVehiculeService> removeReservationVehiculeServiceById(Integer id);
}
