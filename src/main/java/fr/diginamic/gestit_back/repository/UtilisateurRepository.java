package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByNom(String nom);
    Optional<Utilisateur> findByEmail(String email);
    //Optional<Utilisateur> findById(Integer id);
}
