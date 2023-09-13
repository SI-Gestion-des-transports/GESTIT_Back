package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Integer> {

    @Override
    List<Adresse> findAll();
}
