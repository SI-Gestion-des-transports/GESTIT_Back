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

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class ReservationVehiculeServiceService {

    private ReservationVehiculeServiceRepository reservationVehiculeServiceRepository;
    private UtilisateurService utilisateurService;
    private VehiculeServiceService vehiculeServiceService;
    private JWTUtils jwtUtils;

    @Transactional
    public List<ReservationVehiculeService> listeReservationVehiculeService(String JWToken){
        Integer utilisateurConnecteId = Integer.decode(jwtUtils.parseJWT(JWToken).getSubject());
        return this.reservationVehiculeServiceRepository.findReservationVehiculeServiceByCollaborateur(
                this.utilisateurService.trouverParId(utilisateurConnecteId));
    }

    @Transactional
    public void creerReservationVehiculeService(String JWToken, ReservationVehiculeServiceDto res){
        Integer utilisateurConnecteId = Integer.decode(jwtUtils.parseJWT(JWToken).getSubject());
        reservationVehiculeServiceRepository.save(new ReservationVehiculeService(
                this.utilisateurService.trouverParId(utilisateurConnecteId),
                this.vehiculeServiceService.trouverParId(res.vehiculeServiceId()),
                res.dateHeureDepart(),
                res.dateHeureRetour()
                )
        );
    }

    @Transactional
    public void modifierReservationVehiculeService(String JWToken, ReservationVehiculeServiceDto newRes, Integer oldResId){
        Integer utilisateurConnecteId = Integer.decode(jwtUtils.parseJWT(JWToken).getSubject());
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
    public void supprimerReservationVehiculeService(String JWToken, Integer resId){
        Integer utilisateurConnecteId = Integer.decode(jwtUtils.parseJWT(JWToken).getSubject());
            reservationVehiculeServiceRepository.deleteReservationVehiculeServiceByCollaborateur_IdAndAndId(utilisateurConnecteId, resId);
    }

    @Transactional
    @Secured("ADMINISTRATEUR")
    public void adminDeleteAllReservationsByVehiculeServiceId(Integer vehiculeServiceId){
        List<ReservationVehiculeService> reservationsToDelete = reservationVehiculeServiceRepository.findAllByVehiculeServiceId(vehiculeServiceId);
        List<String> emailsOfUsers = reservationsToDelete.stream()
                .map(ReservationVehiculeService::getCollaborateur)
                .map(Utilisateur::getEmail)
                .distinct()
                .toList();
        // Utiliser la liste de mail pour prévenir les utilisateurs

        reservationVehiculeServiceRepository.deleteAllByVehiculeServiceId(vehiculeServiceId);
    }

}
