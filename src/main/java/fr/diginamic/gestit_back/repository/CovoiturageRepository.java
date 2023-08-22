package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Utilisateur;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import fr.diginamic.gestit_back.entites.VehiculePerso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovoiturageRepository extends JpaRepository<Covoiturage, Integer> {
   Set<Covoiturage> findCovoituragesByOrganisateur(Utilisateur utilisateur);
   Covoiturage findCovoiturageByPassagers(Utilisateur utilisateur);

   List<Covoiturage> findCovoituragesByVehiculePersoAndDateDepartIsAfter(VehiculePerso vehiculePerso, LocalDate dateTimeSupprimer);

}
