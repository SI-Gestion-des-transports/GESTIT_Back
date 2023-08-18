package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CovoiturageRepository extends JpaRepository<Covoiturage,Integer>{
   Set<Covoiturage> findCovoituragesByOrganisateur (Utilisateur utilisateur);
}
