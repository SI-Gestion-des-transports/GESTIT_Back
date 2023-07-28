package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.VehiculePerso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculePersoRepository extends JpaRepository<VehiculePerso,Integer> {
    public VehiculePerso findVehiculePersoByImmatriculation(String immatriculation);

}
