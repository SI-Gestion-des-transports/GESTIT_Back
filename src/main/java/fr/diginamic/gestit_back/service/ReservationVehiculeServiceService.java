package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.MessageDto;
import fr.diginamic.gestit_back.dto.ReservationVehiculeServiceDto;
import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.repository.ReservationVehiculeServiceRepository;
import fr.diginamic.gestit_back.utils.JWTUtils;
import fr.diginamic.gestit_back.utils.NotFoundOrValidException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class ReservationVehiculeServiceService {

    private ReservationVehiculeServiceRepository reservationVehiculeServiceRepository;
    private UtilisateurService utilisateurService;
    private VehiculeServiceService vehiculeServiceService;

    @Transactional
    public List<ReservationVehiculeService> listeReservationVehiculeService(Integer utilisateurConnecteId){
        return this.reservationVehiculeServiceRepository.findReservationVehiculeServiceByCollaborateur(
                this.utilisateurService.trouverParId(utilisateurConnecteId));
    }

    @Transactional
    public void creerReservationVehiculeService(Integer utilisateurConnecteId, ReservationVehiculeServiceDto res){
        reservationVehiculeServiceRepository.save(new ReservationVehiculeService(
                this.utilisateurService.trouverParId(utilisateurConnecteId),
                this.vehiculeServiceService.trouverParId(res.vehiculeServiceId()),
                res.dateHeureDepart(),
                res.dateHeureRetour()
                )
        );
    }

    @Transactional
    public void modifierReservationVehiculeService(Integer utilisateurConnecteId, ReservationVehiculeServiceDto newRes, Integer oldResId){
        ReservationVehiculeService reservationVSaModifier = reservationVehiculeServiceRepository.findById(oldResId).orElseThrow();
        if (newRes.userId().equals(utilisateurConnecteId)){
        reservationVSaModifier.setCollaborateur(this.utilisateurService.trouverParId(utilisateurConnecteId));
        reservationVSaModifier.setVehiculeService(this.vehiculeServiceService.trouverParId(newRes.vehiculeServiceId()));
        reservationVSaModifier.setDateHeureDepart(newRes.dateHeureDepart());
        reservationVSaModifier.setDateHeureRetour(newRes.dateHeureRetour());
        reservationVehiculeServiceRepository.save(reservationVSaModifier);
        } else {
            throw new NotFoundOrValidException(new MessageDto("Les utilisateurs ne correspondent pas"));
        }
    }

    @Transactional
    public void supprimerReservationVehiculeService(Integer utilisateurConnecteId, Integer resId){

        reservationVehiculeServiceRepository.deleteReservationVehiculeServiceByCollaborateur_IdAndAndId(utilisateurConnecteId, resId);
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
        //reservationVehiculeServiceRepository.deleteReservationVehiculeServiceByIdAndAndDateHeureDepartIsAfterAndDateHeureRetourIsBefore(vehiculeServiceId, date, date);
        reservationVehiculeServiceRepository.deleteAllByVehiculeServiceId(vehiculeServiceId);
        System.out.println("Supprimer réservations Ok");
    }

}
