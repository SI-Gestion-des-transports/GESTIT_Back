package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.CovoiturageDtoRecord;
import fr.diginamic.gestit_back.dto.MessageDto;
import fr.diginamic.gestit_back.entites.*;
import fr.diginamic.gestit_back.exceptions.CovoiturageNotFoundException;
import fr.diginamic.gestit_back.exceptions.NotFoundOrValidException;
import fr.diginamic.gestit_back.repository.AdresseRepository;
import fr.diginamic.gestit_back.repository.CovoiturageRepository;
import fr.diginamic.gestit_back.repository.UtilisateurRepository;
import fr.diginamic.gestit_back.repository.VehiculePersoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private UtilisateurService utilisateurService;

    public Covoiturage add(Covoiturage covoiturage) {
        return covoiturageRepository.save(covoiturage);
    }

    /**
     * Crée un covoiturage basé sur les données fournies par le DTO.
     * <p>
     * Pour créer un covoiturage, l'utilisateur (identifié par userId) doit avoir au moins un véhicule personnel.
     * La méthode renvoie une exception si aucun véhicule n'est trouvé pour l'utilisateur ou si d'autres problèmes surviennent lors de la création.
     *
     * @param covoiturageDto Le DTO contenant les informations nécessaires pour créer un covoiturage.
     * @param userId         L'ID de l'utilisateur souhaitant créer le covoiturage.
     * @return Le covoiturage nouvellement créé.
     * @throws CovoiturageNotFoundException Si la création du covoiturage échoue pour une raison quelconque.
     */
    @Transactional
    public Covoiturage creerCovoiturage(CovoiturageDtoRecord covoiturageDto, Integer userId) throws CovoiturageNotFoundException {
        Optional<Utilisateur> connectedUser = utilisateurService.rechercheParId(userId);

        if (connectedUser.isPresent() && !connectedUser.get().getVehiculesPerso().isEmpty()) {

            LocalDate dateDepart = covoiturageDto.dateDepart();
            Adresse adresseDepart = adresseRepository.findById(covoiturageDto.adresseDepartId()).orElseThrow();
            Adresse adresseArrivee = adresseRepository.findById(covoiturageDto.adresseArriveeID()).orElseThrow();
            Utilisateur organisateur = utilisateurRepository.findById(connectedUser.get().getId()).orElseThrow();
            VehiculePerso vehiculePerso = vehiculePersoRepository.findVehiculePersoByProprietaire(organisateur).get(0);

            Covoiturage covoiturage = new Covoiturage(covoiturageDto.nombrePlacesRestantes(), covoiturageDto.dureeTrajet(), covoiturageDto.distanceKm(), dateDepart, adresseDepart, adresseArrivee, new ArrayList<>(), organisateur, vehiculePerso);

            return covoiturageRepository.save(covoiturage);
        } else {
            throw new CovoiturageNotFoundException();
        }
    }

    /**
     * Liste tous les covoiturages organisés par un utilisateur spécifié par son ID.
     *
     * @param userId L'ID de l'utilisateur pour lequel les covoiturages organisés doivent être listés.
     * @return Une liste de covoiturages organisés par l'utilisateur. Si l'utilisateur n'est pas trouvé, renvoie null.
     */
    @Transactional
    public List<Covoiturage> listerCovoiturageOrganises(Integer userId) {
        Optional<Utilisateur> connectedUser = utilisateurService.rechercheParId(userId);

        List<Covoiturage> covoituragesOrganises;
        if (connectedUser.isEmpty()) {
            return null;
        }
        covoituragesOrganises = covoiturageRepository.findCovoituragesByOrganisateur(connectedUser.get());
        return covoituragesOrganises;
    }

    /**
     * Ajoute un passager à un covoiturage spécifié par son ID.
     *
     * <p>
     * La méthode vérifie d'abord si le covoiturage existe et s'il reste des places disponibles.
     * Si le covoiturage est complet ou n'existe pas, une exception est levée.
     * Sinon, le passager est ajouté à la liste des passagers du covoiturage, et le nombre de places restantes est décrémenté de 1.
     * </p>
     *
     * @param covoiturageId L'ID du covoiturage auquel le passager doit être ajouté.
     * @param passager      L'utilisateur à ajouter en tant que passager.
     * @return Le covoiturage mis à jour.
     * @throws CovoiturageNotFoundException Si le covoiturage avec l'ID spécifié n'est pas trouvé.
     * @throws NotFoundOrValidException     Si le covoiturage est complet.
     */
    @Transactional
    public Covoiturage addPassenger(Integer covoiturageId, Utilisateur passager) throws CovoiturageNotFoundException {
        Covoiturage covoiturage;
        Optional<Covoiturage> covoiturageOptional = covoiturageRepository.findById(covoiturageId);
        if (covoiturageOptional.isEmpty()) {
            throw new CovoiturageNotFoundException();
        } else {
            covoiturage = covoiturageOptional.get();
            if (covoiturageOptional.get().getNombrePlacesRestantes() <= 0) {
                throw new NotFoundOrValidException(new MessageDto("Il n'y a plus de place dans ce covoiturage"));
            } else {
                List<Utilisateur> passagers = covoiturage.getPassagers();
                passagers.add(utilisateurRepository.findById(passager.getId()).orElseThrow());
                covoiturage.setNombrePlacesRestantes(covoiturage.getNombrePlacesRestantes() - 1);
                covoiturage.setPassagers(passagers);
                return covoiturageRepository.save(covoiturage);
            }
        }
    }

    /**
     * Met à jour un covoiturage existant.
     *
     * <p>
     * Si le covoiturage n'existe pas dans le référentiel, une exception est levée.
     * Sinon, le covoiturage est mis à jour dans la base de données.
     * </p>
     *
     * @param covoiturage Le covoiturage à mettre à jour, identifié par son ID.
     * @return Le covoiturage mis à jour.
     * @throws CovoiturageNotFoundException Si aucun covoiturage avec l'ID spécifié n'est trouvé.
     */
    public Covoiturage update(Covoiturage covoiturage) throws CovoiturageNotFoundException {
        if (!covoiturageRepository.existsById(covoiturage.getId())) {
            throw new CovoiturageNotFoundException();
        }

        return covoiturageRepository.save(covoiturage);
    }

    /**
     * Récupère un covoiturage spécifié par son ID.
     *
     * <p>
     * Si le covoiturage n'existe pas dans le référentiel, une exception est levée.
     * Sinon, le covoiturage correspondant à l'ID donné est renvoyé.
     * </p>
     *
     * @param id L'ID du covoiturage à récupérer.
     * @return Le covoiturage correspondant à l'ID donné.
     * @throws CovoiturageNotFoundException Si aucun covoiturage avec l'ID spécifié n'est trouvé.
     */
    public Covoiturage get(Integer id) throws CovoiturageNotFoundException {
        Optional<Covoiturage> result = covoiturageRepository.findById(id);
        if (result.isEmpty())
            throw new CovoiturageNotFoundException();
        return result.get();
    }

    public List<Covoiturage> list() {
        return covoiturageRepository.findAll();

    }

    public List<CovoiturageDtoRecord> listall() {
        return covoiturageRepository.findAll().stream()
                .map(covoit -> this.changeToCovoitDto(covoit))
                .collect(Collectors.toList());



    }


    public CovoiturageDtoRecord changeToCovoitDto(Covoiturage covoiturage){
        List<Integer> passengersIdList = covoiturage.getPassagers().stream().map(passager -> passager.getId()).collect(Collectors.toList());
        Integer[] allPassengers = passengersIdList.toArray(new Integer[0]);

        return new CovoiturageDtoRecord(
                covoiturage.getId(),
                covoiturage.getNombrePlacesRestantes(),
                covoiturage.getDureeTrajet(),
                covoiturage.getDistanceKm(),
                covoiturage.getDateDepart(),
                covoiturage.getAdresseDepart().getId(),
                covoiturage.getAdresseArrivee().getId(),
                covoiturage.getOrganisateur().getId(),
                covoiturage.getVehiculePerso().getId(),
                allPassengers);
    }


    public void delete(Integer id) throws CovoiturageNotFoundException {

        if (covoiturageRepository.existsById(id)) {

            Optional<Covoiturage> covoiturage = covoiturageRepository.findById(id);
            covoiturage.ifPresent(covoiturage1 -> {
                covoiturage1.setPassagers(new ArrayList<>());
            });
            covoiturageRepository.deleteById(id);
        } else throw new CovoiturageNotFoundException();
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

/*    public Integer testCreate(CovoiturageDtoRecord tcd) throws CovoiturageNotFoundException {
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
        Covoiturage covoiturage = new Covoiturage(tcd.nombrePlacesRestantes(), tcd.dureeTrajet(), tcd.distanceKm(), dateDepart, adresseDepart, adresseArrivee, new ArrayList<>(), organisateur, vehiculePerso);
        System.out.println("****** SAVE ?? ******");
        covoiturageRepository.save(covoiturage);
        System.out.println("****** SAVE OK ******");

*//*
        Set<Utilisateur> passagers = covoiturage.getPassagers();
        passagers.add(utilisateurRepository.findById(7).orElseThrow());
        covoiturage.setNombrePlacesRestantes(covoiturage.getNombrePlacesRestantes() - 1);
        passagers.add(utilisateurRepository.findById(9).orElseThrow());
        covoiturage.setNombrePlacesRestantes(covoiturage.getNombrePlacesRestantes() - 1);
        covoiturage.setPassagers(passagers);
        System.out.println("TEST CREATE");
        *//*


        System.out.println("Sortie testCreate");

        Utilisateur passager = utilisateurRepository.findById(8).get();
        addPassenger(covoiturage.getId(), passager);

        return covoiturage.getId();

    }*/

    public void testFindPassager(Integer covoiturageId) {

        Covoiturage covoiturage = covoiturageRepository.findById(covoiturageId).orElseThrow();
        List<Covoiturage> covoiturages = covoiturageRepository.findCovoituragesByOrganisateur(covoiturage.getOrganisateur());
        Set<Utilisateur> passagers = utilisateurRepository.findUtilisateursByCovoituragesPassagers(covoiturage);

        for (Covoiturage c : covoiturages) {
            System.out.println("Covoiturage ID : " + c.getId());
            for (Utilisateur u : c.getPassagers().stream().toList()) {
                System.out.println(" Passager ID : " + u.getId());
            }
        }
        for (Utilisateur p : passagers) {
            System.out.println("Passager : " + p.getNom());
        }


    }
}
