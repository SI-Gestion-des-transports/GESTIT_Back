package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculePerso;
import fr.diginamic.gestit_back.exceptions.CovoiturageNotFoundException;
import fr.diginamic.gestit_back.repository.CovoiturageRepository;

import fr.diginamic.gestit_back.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Data
@Transactional
public class CovoiturageService {

    //@Autowired
    private CovoiturageRepository covoiturageRepository;
    private UtilisateurRepository utilisateurRepository;

    public Covoiturage add(Covoiturage covoiturage) {
        return covoiturageRepository.save(covoiturage);
    }

    public Covoiturage update(Covoiturage covoiturage) throws CovoiturageNotFoundException {
        if (!covoiturageRepository.existsById(covoiturage.getId())) {
            throw new CovoiturageNotFoundException();
        }
        return covoiturageRepository.save(covoiturage);
    }

    public Covoiturage get(Integer id) throws CovoiturageNotFoundException {
        Optional<Covoiturage> result = covoiturageRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new CovoiturageNotFoundException();
    }

    public List<Covoiturage> list() {
        return covoiturageRepository.findAll();

    }

    public void delete(Integer id) throws CovoiturageNotFoundException {

        if (covoiturageRepository.existsById(id)) {

            Optional<Covoiturage> covoiturage = covoiturageRepository.findById(id);
            covoiturage.ifPresent(covoiturage1 -> {
                covoiturage1.setPassagers(new ArrayList<>());
            });
            covoiturageRepository.deleteById(id);
        }else throw new CovoiturageNotFoundException();
    }

    public List<Covoiturage> findCovoituragesByVehiculePerSupprimer(VehiculePerso vehiculePerso) {
        return covoiturageRepository.findCovoituragesByVehiculePersoAndDateDepartIsAfter(vehiculePerso, LocalDate.now());
    }

    public ResponseEntity testCreatePassageur(Integer userId, Integer conId) {
        Optional<Covoiturage> covoiturage = covoiturageRepository.findById(conId);

        covoiturage.ifPresent(c -> {
            Utilisateur utilisateur = utilisateurRepository.findById(userId).get();
            c.getPassagers().add(utilisateur);

        });
        return ResponseEntity.status(200).body(covoiturage.get());
    }
}
