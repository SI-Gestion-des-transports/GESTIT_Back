package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.ReservationVehiculeServiceDto;
import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import fr.diginamic.gestit_back.service.ReservationVehiculeServiceService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@AllArgsConstructor
@Validated
@RequestMapping("reservation")
public class ReservationVehiculeServiceController {

    ReservationVehiculeServiceService reservationVehiculeServiceService;

    @GetMapping
    public void listerReservations(){
        for (ReservationVehiculeService r : this.reservationVehiculeServiceService.listeReservationVehiculeService()){
            System.out.println("Réservation " + r.getId() + " : " + r);
        }
    }

    @PostMapping("/create")
    public void creerReservationVehiculeService(@RequestBody ReservationVehiculeServiceDto reservationVehiculeServiceDto){
        this.reservationVehiculeServiceService.creerReservationVehiculeService(reservationVehiculeServiceDto);
    }


}
