package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.MessageDto;
import fr.diginamic.gestit_back.dto.ReservationVehiculeServiceDto;
import fr.diginamic.gestit_back.dto.UtilisateurDto;
import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.exceptions.NotFoundOrValidException;
import fr.diginamic.gestit_back.repository.ReservationVehiculeServiceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des réservations de véhicules de service.
 */
@Service
@Data
@AllArgsConstructor
public class ReservationVehiculeServiceService {

    private ReservationVehiculeServiceRepository reservationVehiculeServiceRepository;
    private UtilisateurService utilisateurService;
    private VehiculeServiceService vehiculeServiceService;
    private static final Logger log = LoggerFactory.getLogger(ReservationVehiculeServiceService.class);


    /**
     * Récupère la liste des réservations de véhicule pour un utilisateur donné.
     *
     * @param utilisateurConnecteId L'ID de l'utilisateur connecté.
     * @return La liste des réservations de véhicule.
     */
    @Transactional
    public List<ReservationVehiculeServiceDto> listeReservationVehiculeService(Integer utilisateurConnecteId) {
        return this.reservationVehiculeServiceRepository.findReservationVehiculeServiceByCollaborateur(
                this.utilisateurService.trouverParId(utilisateurConnecteId))
                .stream()
                .map(this::changeToResVSDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère la liste des réservations de véhicule en cours pour un utilisateur donné.
     *
     * @param utilisateurConnecteId L'ID de l'utilisateur connecté.
     * @return La liste des réservations de véhicule en cours.
     */
    @Transactional
    public List<ReservationVehiculeServiceDto> listeReservationVSEnCours(Integer utilisateurConnecteId) {
        return this.reservationVehiculeServiceRepository.findAllReservationsVehiculeServiceByCollaborateurAndDateHeureRetourIsAfter(
                this.utilisateurService.trouverParId(utilisateurConnecteId), LocalDateTime.now())
                .stream()
                .map(this::changeToResVSDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère la liste des réservations de véhicule terminées pour un utilisateur donné.
     *
     * @param utilisateurConnecteId L'ID de l'utilisateur connecté.
     * @return La liste des réservations de véhicule terminées.
     */
    @Transactional
    public List<ReservationVehiculeServiceDto> listeReservationVSHistorique(Integer utilisateurConnecteId) {
        return this.reservationVehiculeServiceRepository.findAllReservationsVehiculeServiceByCollaborateurAndDateHeureRetourIsBefore(
                this.utilisateurService.trouverParId(utilisateurConnecteId), LocalDateTime.now())
                .stream()
                .map(this::changeToResVSDto)
                .collect(Collectors.toList());
    }

    /**
     * Crée une nouvelle réservation de véhicule.
     *
     * @param utilisateurConnecteId L'ID de l'utilisateur connecté.
     * @param res                   DTO de la réservation de véhicule.
     */
    @Transactional
    public void creerReservationVehiculeService(Integer utilisateurConnecteId, ReservationVehiculeServiceDto res) {

        Optional<Utilisateur> connectedUser = utilisateurService.rechercheParId(utilisateurConnecteId);

        Optional<List<ReservationVehiculeService>> overllapingReservationsList = overlappingReservations(res);

        if (overllapingReservationsList.isPresent() || connectedUser.isEmpty()) {
            throw new NotFoundOrValidException(new MessageDto("Create - La réservation est impossible à ces dates !"));
        } else if (res.dateHeureDepart().isAfter(res.dateHeureRetour())) {
            throw new NotFoundOrValidException(new MessageDto("Create - La date de retour doit être postérieure à la date de départ !"));
        } else if (!res.userId().equals(utilisateurConnecteId)) {
            throw new NotFoundOrValidException(new MessageDto("Create - L'utilisateur n'est pas bon !"));
        } else {
            reservationVehiculeServiceRepository.save(new ReservationVehiculeService(
                            connectedUser.orElseThrow(),
                            this.vehiculeServiceService.trouverParId(res.vehiculeServiceId()),
                            res.dateHeureDepart(),
                            res.dateHeureRetour()
                    )
            );
        }
    }

    /**
     * Modifie une réservation de véhicule existante.
     *
     * @param utilisateurConnecteId L'ID de l'utilisateur connecté.
     * @param newRes                Nouveau DTO de la réservation de véhicule.
     * @param oldResId              ID de l'ancienne réservation de véhicule.
     */
    @Transactional
    public void modifierReservationVehiculeService(Integer utilisateurConnecteId, ReservationVehiculeServiceDto newRes, Integer oldResId) {
        ReservationVehiculeService reservationVSaModifier = reservationVehiculeServiceRepository.findById(oldResId).orElseThrow();

        Optional<List<ReservationVehiculeService>> optionalOverlappedRVS = overlappingReservations(newRes);

        List<ReservationVehiculeService> overlappedRVS = new ArrayList<>();

        if (optionalOverlappedRVS.isPresent()) {
            overlappedRVS = optionalOverlappedRVS.get();
            if (overlappedRVS.contains(reservationVSaModifier)) {
                overlappedRVS.remove(reservationVSaModifier);
            }
        }
        if (newRes.userId().equals(utilisateurConnecteId) && overlappedRVS.isEmpty() && newRes.dateHeureDepart().isBefore(newRes.dateHeureRetour())) {
            reservationVSaModifier.setCollaborateur(this.utilisateurService.trouverParId(utilisateurConnecteId));
            reservationVSaModifier.setVehiculeService(this.vehiculeServiceService.trouverParId(newRes.vehiculeServiceId()));
            reservationVSaModifier.setDateHeureDepart(newRes.dateHeureDepart());
            reservationVSaModifier.setDateHeureRetour(newRes.dateHeureRetour());
            reservationVehiculeServiceRepository.save(reservationVSaModifier);
        } else {
            throw new NotFoundOrValidException(new MessageDto("MOD - La modification n'est pas possible !"));
        }
    }

    /**
     * Supprime une réservation de véhicule.
     *
     * @param utilisateurConnecteId L'ID de l'utilisateur connecté.
     * @param resId                 ID de la réservation de véhicule à supprimer.
     */
    @Transactional
    public void supprimerReservationVehiculeService(Integer utilisateurConnecteId, Integer resId) {
        if (this.reservationVehiculeServiceRepository.findById(resId).isPresent() && !this.reservationVehiculeServiceRepository.findById(resId).isEmpty()) {
            ReservationVehiculeService reservationVS = this.reservationVehiculeServiceRepository.findById(resId).get();
            if (reservationVS.getDateHeureDepart().isBefore(LocalDateTime.now()) && reservationVS.getDateHeureRetour().isAfter(LocalDateTime.now())) {
                throw new NotFoundOrValidException(new MessageDto("La suppression n'est plus possible, la réservation est en cours"));
            }
            reservationVehiculeServiceRepository.deleteReservationVehiculeServiceByCollaborateur_IdAndAndId(utilisateurConnecteId, resId);
        }
    }

    /**
     * Suppression administrative de toutes les réservations associées à un ID de véhicule donné et après une certaine date.
     * Seuls les administrateurs peuvent exécuter cette méthode.
     *
     * @param vehiculeServiceId L'ID du service de véhicule.
     * @param date              Date à partir de laquelle les réservations doivent être supprimées.
     */
    @Transactional
    //@Secured("ADMINISTRATEUR")
    public void adminDeleteAllReservationsByVehiculeServiceId(Integer vehiculeServiceId, LocalDateTime date) {

        List<ReservationVehiculeService> reservationsToDelete = reservationVehiculeServiceRepository.findAllByVehiculeServiceIdAndAndDateHeureDepart(vehiculeServiceId, date);

        List<String> emailsOfUsers = reservationsToDelete.stream()
                .map(ReservationVehiculeService::getCollaborateur)
                .map(Utilisateur::getEmail)
                .distinct()
                .toList();
        // Utiliser la liste de mail pour prévenir les utilisateurs

        reservationVehiculeServiceRepository.deleteReservationVehiculeServiceByIdAndAndDateHeureDepartIsAfterAndDateHeureRetourIsBefore(vehiculeServiceId, date, date);
    }

    /**
     * Récupère la liste des réservations en chevauchement avec une réservation donnée.
     *
     * @param res DTO de la réservation de véhicule.
     * @return Une option contenant la liste des réservations en chevauchement ou un optionnel vide si aucun chevauchement n'est trouvé.
     */
    public Optional<List<ReservationVehiculeService>> overlappingReservations(ReservationVehiculeServiceDto res) {

        Optional<List<ReservationVehiculeService>> overllapingReservationsList = reservationVehiculeServiceRepository.findOverlappingReservations(
                res.vehiculeServiceId(),
                res.dateHeureDepart(),
                res.dateHeureRetour()
        );

        // Si la liste est présente et non vide, retour de la liste, sinon retour d'un optionnel vide
        if (overllapingReservationsList.isPresent() && !overllapingReservationsList.get().isEmpty()) {
            return overllapingReservationsList;
        } else {
            return Optional.empty();
        }
    }

    public ReservationVehiculeServiceDto changeToResVSDto(ReservationVehiculeService resVS){
        return new ReservationVehiculeServiceDto(resVS.getId(), resVS.getVehiculeService().getId(), resVS.getDateHeureDepart(), resVS.getDateHeureRetour());
    }
}
