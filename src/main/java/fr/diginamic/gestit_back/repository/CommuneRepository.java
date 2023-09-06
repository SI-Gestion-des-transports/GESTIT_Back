package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommuneRepository extends JpaRepository<Commune, Integer> {

    Optional<Commune> findCommuneByNomAndCodePostal(String nom, Integer codePostal);
}
