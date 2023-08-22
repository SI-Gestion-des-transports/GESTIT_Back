package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.MessageDto;
import fr.diginamic.gestit_back.dto.TestCovoiturageDto;
import fr.diginamic.gestit_back.entites.Adresse;
import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculePerso;
import fr.diginamic.gestit_back.exceptions.CovoiturageNotFoundException;
import fr.diginamic.gestit_back.repository.AdresseRepository;
import fr.diginamic.gestit_back.repository.CovoiturageRepository;
import fr.diginamic.gestit_back.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import fr.diginamic.gestit_back.repository.VehiculePersoRepository;
import fr.diginamic.gestit_back.exceptions.NotFoundOrValidException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.*;


@Service
@AllArgsConstructor
@Data
@Transactional
public class CovoiturageService {

    //@Autowired
    private CovoiturageRepository covoiturageRepository;
    private UtilisateurRepository utilisateurRepository;
    private AdresseRepository adresseRepository;
    private VehiculePersoRepository vehiculePersoRepository;

    public Covoiturage add(Covoiturage covoiturage) {
        return covoiturageRepository.save(covoiturage);
    }

    public Covoiturage addPassenger (Integer covoiturageId, Utilisateur passager) throws CovoiturageNotFoundException {
        Covoiturage covoiturage;
        Optional<Covoiturage> covoiturageOptional = covoiturageRepository.findById(covoiturageId);
        if (covoiturageOptional.isEmpty()) {
            throw new CovoiturageNotFoundException();
        } else {
            covoiturage = covoiturageOptional.get();
            if (covoiturageOptional.get().getNombrePlacesRestantes() <= 0) {
                throw new NotFoundOrValidException(new MessageDto("Il n'y a plus de place dans ce covoiturage"));
            } else {
                Set<Utilisateur> passagers = covoiturage.getPassagers();
                passagers.add(utilisateurRepository.findById(passager.getId()).orElseThrow());
                covoiturage.setNombrePlacesRestantes(covoiturage.getNombrePlacesRestantes() - 1);
                covoiturage.setPassagers(passagers);
                return covoiturageRepository.save(covoiturage);
            }
        }
    }

    public Covoiturage update(Covoiturage covoiturage) throws CovoiturageNotFoundException {
        if (!covoiturageRepository.existsById(covoiturage.getId())) {
            throw new CovoiturageNotFoundException();
        }

        return covoiturageRepository.save(covoiturage);
    }

    public Covoiturage get(Integer id) throws CovoiturageNotFoundException {
        Optional<Covoiturage> result = covoiturageRepository.findById(id);
        if (result.isEmpty())
            throw new CovoiturageNotFoundException();
        return result.get();
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

    public Integer testCreate(TestCovoiturageDto tcd) throws CovoiturageNotFoundException {
        System.out.println("Entrée testCreate");
        LocalDate dateDepart = tcd.dateDepart();
        Adresse adresseDepart = adresseRepository.findById(tcd.adresseDepartId()).orElseThrow();
        System.out.println("adresseDepart" + adresseDepart.getVoie());
        Adresse adresseArrivee = adresseRepository.findById(tcd.adresseArriveeID()).orElseThrow();
        System.out.println("adresseArrivee" + adresseArrivee.getVoie());
        Utilisateur organisateur = utilisateurRepository.findById(tcd.organisateurId()).orElseThrow();
        System.out.println("organisateur" + organisateur.getNom());
        VehiculePerso vehiculePerso = vehiculePersoRepository.findVehiculePersoByProprietaire(organisateur).get(0);
        System.out.println("vehiculePerso" + vehiculePerso.getImmatriculation());

        System.out.println("****** New Covoit ******");
        Covoiturage covoiturage = new Covoiturage(tcd.nombrePlacesRestantes(), tcd.dureeTrajet(), tcd.distanceKm(), dateDepart, adresseDepart, adresseArrivee, new HashSet<>(), organisateur, vehiculePerso);
        System.out.println("****** SAVE ?? ******");
        covoiturageRepository.save(covoiturage);
        System.out.println("****** SAVE OK ******");

/*
        Set<Utilisateur> passagers = covoiturage.getPassagers();
        passagers.add(utilisateurRepository.findById(7).orElseThrow());
        covoiturage.setNombrePlacesRestantes(covoiturage.getNombrePlacesRestantes() - 1);
        passagers.add(utilisateurRepository.findById(9).orElseThrow());
        covoiturage.setNombrePlacesRestantes(covoiturage.getNombrePlacesRestantes() - 1);
        covoiturage.setPassagers(passagers);
        System.out.println("TEST CREATE");
        */


        System.out.println("Sortie testCreate");

        Utilisateur passager = utilisateurRepository.findById(8).get();
        addPassenger(covoiturage.getId(), passager);

        return covoiturage.getId();

    }

    public void testFindPassager(Integer covoiturageId){

        Covoiturage covoiturage = covoiturageRepository.findById(covoiturageId).orElseThrow();
        Set<Covoiturage> covoiturages = covoiturageRepository.findCovoituragesByOrganisateur(covoiturage.getOrganisateur());
        Set<Utilisateur> passagers = utilisateurRepository.findUtilisateursByCovoituragesPassagers(covoiturage);

        for (Covoiturage c:covoiturages){
            System.out.println("Covoiturage ID : " + c.getId());
            for (Utilisateur u:c.getPassagers().stream().toList()){
                System.out.println(" Passager ID : " +u.getId());
            }
        }
        for (Utilisateur p : passagers){
            System.out.println("Passager : " + p.getNom());
        }


    }
}
