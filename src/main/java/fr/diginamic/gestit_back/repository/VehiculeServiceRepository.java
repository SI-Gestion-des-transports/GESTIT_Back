package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.VehiculeService;
import fr.diginamic.gestit_back.enumerations.Statut;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculeServiceRepository extends JpaRepository<VehiculeService, Integer> {

     Optional<VehiculeService> findById(Integer id);
     List<VehiculeService> findVehiculeServiceByStatut(Statut statut);

}
