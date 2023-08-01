package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.VehiculeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehiculeServiceRepository extends JpaRepository<VehiculeService, Integer> {

    public Optional<VehiculeService> findById(Integer id);
    public VehiculeService findFirstByImmatriculation(String immatriculation);
}
