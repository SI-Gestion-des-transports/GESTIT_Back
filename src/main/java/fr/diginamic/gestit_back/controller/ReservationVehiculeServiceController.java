package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.ReservationVehiculeServiceDto;
import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import fr.diginamic.gestit_back.service.ReservationVehiculeServiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@AllArgsConstructor
@Validated
@Secured("COLLABORATEUR")
@RequestMapping("reservation")
public class ReservationVehiculeServiceController {

    ReservationVehiculeServiceService reservationVehiculeServiceService;

    @GetMapping
    public ResponseEntity<List<ReservationVehiculeService>> listerReservations(@RequestHeader HttpHeaders httpHeaders){
        return ResponseEntity.status(200).body(this.reservationVehiculeServiceService.listeReservationVehiculeService(httpHeaders.get("JWT-TOKEN").get(0)));
    }


    @PostMapping("/create")
    public ResponseEntity<List<ReservationVehiculeService>>  creerReservationVehiculeService(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestBody @Valid ReservationVehiculeServiceDto resDto){
        String JWToken = httpHeaders.get("JWT-TOKEN").get(0);
        this.reservationVehiculeServiceService.creerReservationVehiculeService(JWToken, resDto);
        return ResponseEntity.status(200).body(reservationVehiculeServiceService.listeReservationVehiculeService(JWToken));
    }

    @PostMapping("/modify")
    public ResponseEntity<List<ReservationVehiculeService>> modifierReservationVehiculeService(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestBody @Valid ReservationVehiculeServiceDto newResDto,
            @RequestParam Integer resId){
        String JWToken = httpHeaders.get("JWT-TOKEN").get(0);
        this.reservationVehiculeServiceService.modifierReservationVehiculeService(JWToken, newResDto, resId);
        return ResponseEntity.status(200).body(reservationVehiculeServiceService.listeReservationVehiculeService(JWToken));
    }

    @Secured({"COLLABORATEUR", "ADMINISTRATEUR"})
    @PostMapping("/delete")
    public ResponseEntity<List<ReservationVehiculeService>> supprimerReservationVehiculeService(
            @RequestHeader HttpHeaders httpHeaders,
            //@RequestBody @Valid ReservationVehiculeServiceDto resDto,
            @RequestParam Integer resId){
        String JWToken = httpHeaders.get("JWT-TOKEN").get(0);
        this.reservationVehiculeServiceService.supprimerReservationVehiculeService(JWToken, resId);
        return ResponseEntity.status(200).body(reservationVehiculeServiceService.listeReservationVehiculeService(JWToken));
    }


}
