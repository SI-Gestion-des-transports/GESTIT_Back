package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.VehiculeServiceDto;
import fr.diginamic.gestit_back.entites.VehiculeService;
import fr.diginamic.gestit_back.enumerations.Statut;
import fr.diginamic.gestit_back.service.ReservationVehiculeServiceService;
import fr.diginamic.gestit_back.service.UtilisateurService;
import fr.diginamic.gestit_back.service.VehiculeServiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
//@Secured("ADMINISTRATEUR")
@RequestMapping("admin/vehiculeservice")
@CrossOrigin
public class VehiculeServiceController {
    private VehiculeServiceService vehiculeServiceService;
    private ReservationVehiculeServiceService reservationVehiculeServiceService;

    @PostMapping("/create")
    public ResponseEntity createVehiculeService(@Validated @RequestBody VehiculeServiceDto dto) {
        System.out.println(dto);
        vehiculeServiceService.createVehiculeService(dto);
        return ResponseEntity.status(200).build();
    }

    //@Secured({"COLLABORATEUR", "ADMINISTRATEUR"})
    @GetMapping("/list")
    public ResponseEntity<List<VehiculeServiceDto>> listVehiculeService() {
        return ResponseEntity.status(200).body(vehiculeServiceService.listVehiculeServiceEnService());
    }


    //@Secured("ADMINISTRATEUR")
    @DeleteMapping("/delete")
    public ResponseEntity deleteVehiculeService(@RequestParam Integer id) {

        // Suppression des reservations liées au véhicule de service supprimé
        LocalDateTime date = LocalDateTime.now();
        reservationVehiculeServiceService.adminDeleteAllReservationsByVehiculeServiceId(id, date);


        vehiculeServiceService.deleteVehiculeService(id);
        return ResponseEntity.status(200).build();
    }

    @PutMapping("/modify")
    public ResponseEntity<List<VehiculeServiceDto>> modifyVehiculeService(@Validated @RequestBody VehiculeServiceDto dto) {
        if (dto.getStatut() != Statut.EN_SERVICE)
            reservationVehiculeServiceService.adminDeleteAllReservationsByVehiculeServiceId(dto.getId(), LocalDateTime.now());
        vehiculeServiceService.modifyVehiclueService(dto);
        return ResponseEntity.status(200).body(vehiculeServiceService.listVehiculeService(0, 5));
    }

    @GetMapping("/listall")
    public ResponseEntity<List<VehiculeServiceDto>> findAll(){
        return ResponseEntity.status(200).body(vehiculeServiceService.listAllVehiculeService());
    }

    @GetMapping("/getbyid")
    public ResponseEntity<VehiculeServiceDto> getVsByid(@RequestParam Integer id){
        VehiculeService vs=vehiculeServiceService.getVsById(id);
        return ResponseEntity.status(200).body(new VehiculeServiceDto(vs));

    }
}
