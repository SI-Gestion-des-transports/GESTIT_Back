package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Utilisateur;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovoiturageRepository extends JpaRepository<Covoiturage, Integer> {
   Set<Covoiturage> findCovoituragesByOrganisateur(Utilisateur utilisateur);


}
