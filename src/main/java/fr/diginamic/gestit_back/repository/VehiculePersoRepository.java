package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculePerso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculePersoRepository extends JpaRepository<VehiculePerso,Integer> {
     VehiculePerso findVehiculePersoByImmatriculation(String immatriculation);
     List<VehiculePerso> findVehiculePersoByProprietaire(Utilisateur utilisateur);


}
