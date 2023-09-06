package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.Marque;
import fr.diginamic.gestit_back.entites.Modele;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeleRepository extends JpaRepository<Modele, Integer> {
    Modele findModeleByNom(String nom);
    List<Modele> findModeleByMarque(Marque marque);
}
