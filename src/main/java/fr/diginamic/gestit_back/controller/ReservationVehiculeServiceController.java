package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.ReservationVehiculeServiceDto;
import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import fr.diginamic.gestit_back.service.ReservationVehiculeServiceService;
import fr.diginamic.gestit_back.utils.JWTUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@Data
//@AllArgsConstructor
@Validated
@Secured("COLLABORATEUR")
@RequestMapping("reservation")
public class ReservationVehiculeServiceController {

    private ReservationVehiculeServiceService reservationVehiculeServiceService;

    private JWTUtils jwtUtils;
    @GetMapping
    public ResponseEntity<List<ReservationVehiculeService>> listerReservations(@RequestHeader HttpHeaders httpHeaders){

        Integer utilisateurConnecteId = Integer.decode(jwtUtils.parseJWT(httpHeaders.get("JWT-TOKEN").get(0)).getSubject());
        return ResponseEntity.status(200).body(this.reservationVehiculeServiceService.listeReservationVehiculeService(utilisateurConnecteId));
    }

    @PostMapping("/create")
    public ResponseEntity<List<ReservationVehiculeService>>  creerReservationVehiculeService(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestBody @Valid ReservationVehiculeServiceDto resDto){
        Integer utilisateurConnecteId = Integer.decode(jwtUtils.parseJWT(httpHeaders.get("JWT-TOKEN").get(0)).getSubject());
        this.reservationVehiculeServiceService.creerReservationVehiculeService(utilisateurConnecteId, resDto);
        return ResponseEntity.status(200).body(this.reservationVehiculeServiceService.listeReservationVehiculeService(utilisateurConnecteId));
    }

    @PostMapping("/modify")
    public ResponseEntity<List<ReservationVehiculeService>> modifierReservationVehiculeService(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestBody @Valid ReservationVehiculeServiceDto newResDto,
            @RequestParam Integer resId){
        Integer utilisateurConnecteId = Integer.decode(jwtUtils.parseJWT(httpHeaders.get("JWT-TOKEN").get(0)).getSubject());
        this.reservationVehiculeServiceService.modifierReservationVehiculeService(utilisateurConnecteId, newResDto, resId);
        return ResponseEntity.status(200).body(this.reservationVehiculeServiceService.listeReservationVehiculeService(utilisateurConnecteId));
    }

    @PostMapping("/delete")
    public ResponseEntity<List<ReservationVehiculeService>> supprimerReservationVehiculeService(
            @RequestHeader HttpHeaders httpHeaders,
            //@RequestBody @Valid ReservationVehiculeServiceDto resDto,
            @RequestParam Integer resId){
        Integer utilisateurConnecteId = Integer.decode(jwtUtils.parseJWT(httpHeaders.get("JWT-TOKEN").get(0)).getSubject());
        this.reservationVehiculeServiceService.supprimerReservationVehiculeService(utilisateurConnecteId, resId);
        return ResponseEntity.status(200).body(this.reservationVehiculeServiceService.listeReservationVehiculeService(utilisateurConnecteId));
    }

    @Autowired
    public ReservationVehiculeServiceController(
            ReservationVehiculeServiceService reservationVehiculeServiceService,
            JWTUtils jwtUtils) {
        this.reservationVehiculeServiceService = reservationVehiculeServiceService;
        this.jwtUtils = jwtUtils;
    }

}
