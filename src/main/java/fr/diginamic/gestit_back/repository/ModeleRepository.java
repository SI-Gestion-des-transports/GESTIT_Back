package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.Modele;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeleRepository extends JpaRepository<Modele, Integer> {
}
