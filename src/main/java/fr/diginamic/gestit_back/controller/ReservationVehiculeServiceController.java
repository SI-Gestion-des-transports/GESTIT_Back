package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.ReservationVehiculeServiceDto;
import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import fr.diginamic.gestit_back.service.ReservationVehiculeServiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@AllArgsConstructor
@Validated
@RequestMapping("reservation")
public class ReservationVehiculeServiceController {

    ReservationVehiculeServiceService reservationVehiculeServiceService;

    @GetMapping
    public List<ReservationVehiculeService> listerReservations(){
        for (ReservationVehiculeService r : this.reservationVehiculeServiceService.listeReservationVehiculeService()){
            System.out.println("Réservation " + r.getId() + " : " + r);
        }
        return this.reservationVehiculeServiceService.listeReservationVehiculeService();
    }

    //@Secured("COLLABORATEUR")
    @PostMapping("/create")
    public void creerReservationVehiculeService(@RequestBody @Valid ReservationVehiculeServiceDto resDto){
        this.reservationVehiculeServiceService.creerReservationVehiculeService(resDto);
    }

    //@Secured("COLLABORATEUR")
    @PostMapping("/modify")
    public void modifierReservationVehiculeService(@RequestBody @Valid ReservationVehiculeServiceDto newResDto, @RequestParam Integer resId){
        this.reservationVehiculeServiceService.modifierReservationVehiculeService(newResDto, resId);
    }

    @PostMapping("/delete")
    public void supprimerReservationVehiculeService(@RequestBody @Valid ReservationVehiculeServiceDto resDto,@RequestParam Integer resId){
        this.reservationVehiculeServiceService.supprimerReservationVehiculeService(resId, resDto);
    }


}
