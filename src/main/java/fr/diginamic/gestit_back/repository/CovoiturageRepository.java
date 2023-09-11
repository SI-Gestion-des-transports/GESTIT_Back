package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Utilisateur;
import java.time.LocalDate;
import java.util.List;


import fr.diginamic.gestit_back.entites.VehiculePerso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovoiturageRepository extends JpaRepository<Covoiturage, Integer> {
   List<Covoiturage> findCovoituragesByOrganisateur(Utilisateur organisateur);
   Covoiturage findCovoiturageByPassagers(Utilisateur utilisateur);

   List<Covoiturage> findCovoituragesByVehiculePersoAndDateDepartIsAfter(VehiculePerso vehiculePerso, LocalDate dateTimeSupprimer);


   List<Covoiturage> findCovoituragesByOrganisateurIdAndDateDepartBefore(Integer organisateurId, LocalDate date);
   List<Covoiturage> findCovoituragesByOrganisateurIdAndDateDepartAfter(Integer organisateurId, LocalDate date);

}
