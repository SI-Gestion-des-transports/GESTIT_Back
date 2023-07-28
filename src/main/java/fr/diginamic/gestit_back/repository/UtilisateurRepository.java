package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByNom(String nom);
    Optional<Utilisateur> findByEmail(String email);
}
