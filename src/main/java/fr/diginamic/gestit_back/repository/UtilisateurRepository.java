package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Set<Utilisateur> findUtilisateursByCovoituragesPassagers(Covoiturage covoiturage);

    Optional<Utilisateur> findByNom(String nom);

    Optional<Utilisateur> findByEmail(String email);

    void deleteById(Integer id);

}
