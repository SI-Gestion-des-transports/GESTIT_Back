package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Integer> {
}
