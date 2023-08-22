package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.MessageDto;
import fr.diginamic.gestit_back.dto.ReservationVehiculeServiceDto;
import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.repository.ReservationVehiculeServiceRepository;
import fr.diginamic.gestit_back.exceptions.NotFoundOrValidException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class ReservationVehiculeServiceService {

    private ReservationVehiculeServiceRepository reservationVehiculeServiceRepository;
    private UtilisateurService utilisateurService;
    private VehiculeServiceService vehiculeServiceService;
    private static final Logger log = LoggerFactory.getLogger(ReservationVehiculeServiceService.class);


    @Transactional
    public List<ReservationVehiculeService> listeReservationVehiculeService(Integer utilisateurConnecteId){
        return this.reservationVehiculeServiceRepository.findReservationVehiculeServiceByCollaborateur(
                this.utilisateurService.trouverParId(utilisateurConnecteId));
    }

    @Transactional
    public List<ReservationVehiculeService> listeReservationVSEnCours(Integer utilisateurConnecteId){
        return this.reservationVehiculeServiceRepository.findAllReservationsVehiculeServiceByCollaborateurAndDateHeureRetourIsAfter(this.utilisateurService.trouverParId(utilisateurConnecteId), LocalDateTime.now());
    }

    @Transactional
    public List<ReservationVehiculeService> listeReservationVSHistorique(Integer utilisateurConnecteId){
        return this.reservationVehiculeServiceRepository.findAllReservationsVehiculeServiceByCollaborateurAndDateHeureRetourIsBefore(this.utilisateurService.trouverParId(utilisateurConnecteId), LocalDateTime.now());
    }

    @Transactional
    public void creerReservationVehiculeService(Integer utilisateurConnecteId, ReservationVehiculeServiceDto res){
        System.out.println("Create");
        Optional<Utilisateur> connectedUser = utilisateurService.rechercheParId(utilisateurConnecteId);
        log.info("Create - Création de réservation pour l'utilisateur ID: {}", utilisateurConnecteId);
        if (connectedUser.isEmpty()) {
            log.warn("Create - Utilisateur avec ID {} non trouvé", utilisateurConnecteId);
        }
        System.out.println("————— CALL - overlappingReservations");
        Optional<List<ReservationVehiculeService>> overllapingReservationsList = overlappingReservations(res);
        System.out.println("————— END - overlappingReservations");
        if(overllapingReservationsList.isPresent()){
            System.out.println("Create - overllapingReservationsList présent");
            for (ReservationVehiculeService r : overllapingReservationsList.get()){
            System.out.println("Create - Test");
            System.out.println("Create - Réservation : " + r.getId() + " - " + r.getVehiculeService());
            }
        }
        if (overllapingReservationsList.isPresent() || connectedUser.isEmpty()) {
            throw new NotFoundOrValidException(new MessageDto("Create - La réservation est impossible !"));
        } else {
            log.info("Create - *** connectedUser : " + connectedUser.get().getId());
            log.info("Create - *** vehiculeService : " + this.vehiculeServiceService.trouverParId(res.vehiculeServiceId()));
            log.info("Create - *** dateHeureDepart : " + res.dateHeureDepart());
            log.info("Create - *** dateHeureRetour : " + res.dateHeureRetour());
            reservationVehiculeServiceRepository.save(new ReservationVehiculeService(
                    connectedUser.orElseThrow(),
                    this.vehiculeServiceService.trouverParId(res.vehiculeServiceId()),
                    res.dateHeureDepart(),
                    res.dateHeureRetour()
                    )
            );
        }
    }

    @Transactional
    public void modifierReservationVehiculeService(Integer utilisateurConnecteId, ReservationVehiculeServiceDto newRes, Integer oldResId){
        ReservationVehiculeService reservationVSaModifier = reservationVehiculeServiceRepository.findById(oldResId).orElseThrow();
        System.out.println("MOD");
        System.out.println("————— CALL - overlappingReservations");
        Optional<List<ReservationVehiculeService>> optionalOverlappedRVS = overlappingReservations(newRes);
        System.out.println("————— END - overlappingReservations");
        System.out.println("MOD - optionalOverlappedRVS");
        List<ReservationVehiculeService> overlappedRVS = new ArrayList<>();

        if(optionalOverlappedRVS.isPresent()){
            System.out.println("MOD - optionalOverlappedRVS.isPresent");
            overlappedRVS = optionalOverlappedRVS.get();
            if(overlappedRVS.contains(reservationVSaModifier)){
                System.out.println(overlappedRVS.size());
                System.out.println("MOD - overlappedRVS.contains(reservationVSaModifier)");
                overlappedRVS.remove(reservationVSaModifier);
                System.out.println(overlappedRVS.size());
            }
        }
        if (/*newRes.userId().equals(utilisateurConnecteId) && */overlappedRVS.isEmpty()){
        reservationVSaModifier.setCollaborateur(this.utilisateurService.trouverParId(utilisateurConnecteId));
        reservationVSaModifier.setVehiculeService(this.vehiculeServiceService.trouverParId(newRes.vehiculeServiceId()));
        reservationVSaModifier.setDateHeureDepart(newRes.dateHeureDepart());
        reservationVSaModifier.setDateHeureRetour(newRes.dateHeureRetour());
        reservationVehiculeServiceRepository.save(reservationVSaModifier);
        } else {
            throw new NotFoundOrValidException(new MessageDto("MOD - La modification n'est pas possible !"));
        }
    }

    @Transactional
    public void supprimerReservationVehiculeService(Integer utilisateurConnecteId, Integer resId){
        if (this.reservationVehiculeServiceRepository.findById(resId).isPresent() && !this.reservationVehiculeServiceRepository.findById(resId).isEmpty()){
            ReservationVehiculeService reservationVS = this.reservationVehiculeServiceRepository.findById(resId).get();
            if(reservationVS.getDateHeureDepart().isBefore(LocalDateTime.now()) || reservationVS.getDateHeureRetour().isAfter(LocalDateTime.now())){
                throw new NotFoundOrValidException(new MessageDto("La suppression n'est plus possible, la réservation est en cours"));
            }
            reservationVehiculeServiceRepository.deleteReservationVehiculeServiceByCollaborateur_IdAndAndId(utilisateurConnecteId, resId);
        }
    }

    @Transactional
    @Secured("ADMINISTRATEUR")
    public void adminDeleteAllReservationsByVehiculeServiceId(Integer vehiculeServiceId, LocalDateTime date){
        System.out.println("Lister réservation à faire");
        //List<ReservationVehiculeService> reservationsToDelete = reservationVehiculeServiceRepository.findAllByVehiculeServiceId(vehiculeServiceId);
        List<ReservationVehiculeService> reservationsToDelete = reservationVehiculeServiceRepository.findAllByVehiculeServiceIdAndAndDateHeureDepart(vehiculeServiceId, date);
        List<String> emailsOfUsers = reservationsToDelete.stream()
                .map(ReservationVehiculeService::getCollaborateur)
                .map(Utilisateur::getEmail)
                .distinct()
                .toList();
        // Utiliser la liste de mail pour prévenir les utilisateurs

        System.out.println("Lister réservation Ok");
        //reservationVehiculeServiceRepository.deleteAllByVehiculeServiceId(vehiculeServiceId);
        System.out.println("Supprimer réservations à faire");
        reservationVehiculeServiceRepository.deleteReservationVehiculeServiceByIdAndAndDateHeureDepartIsAfterAndDateHeureRetourIsBefore(vehiculeServiceId, date, date);
        //reservationVehiculeServiceRepository.deleteAllByVehiculeServiceId(vehiculeServiceId);
        System.out.println("Supprimer réservations Ok");
    }

    public Optional<List<ReservationVehiculeService>> overlappingReservations(ReservationVehiculeServiceDto res){
        System.out.println("OV - ***** start overlappingReservations");
        Optional<List<ReservationVehiculeService>> overllapingReservationsList = reservationVehiculeServiceRepository.findOverlappingReservations(
                res.vehiculeServiceId(),
                res.dateHeureDepart(),
                res.dateHeureRetour()
        );
        System.out.println("OV - ***** overllapingReservationsList");
        if (overllapingReservationsList.isPresent() && !overllapingReservationsList.get().isEmpty()){
            System.out.println("OV - ***** overllapingReservationsList : isPresent && !isEmpty ");
            System.out.println(overllapingReservationsList.get());
            for (ReservationVehiculeService r : overllapingReservationsList.get()){
                System.out.println("OV - Réservation : " + r.getId() + " - " + r.getVehiculeService() + " - " + r.getDateHeureDepart() + " - " + r.getDateHeureRetour());
            }
            System.out.println("OV - ***** end overlappingReservations");
            //throw new NotFoundOrValidException(new MessageDto("La réservation n'est pas possible : les dates se chevauchent !"));
            return overllapingReservationsList;
        }
        System.out.println("OV - ***** end overlappingReservations");
        return Optional.empty();
    }

}
