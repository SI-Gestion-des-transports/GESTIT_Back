package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.VehiculeServiceDto;
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
@Secured("ADMINISTRATEUR")
@RequestMapping("admin/vehiculeservice")
public class VehiculeServiceController {
    private VehiculeServiceService vehiculeServiceService;
    private ReservationVehiculeServiceService reservationVehiculeServiceService;

    @PostMapping("/create")
    public ResponseEntity<List<VehiculeServiceDto>> createVehiculeService(@Validated @RequestBody VehiculeServiceDto dto) {
        vehiculeServiceService.createVehiculeService(dto);
        return ResponseEntity.status(200).body(vehiculeServiceService.listVehiculeService(0, 5));
    }

    @Secured({"COLLABORATEUR", "ADMINISTRATEUR"})
    @GetMapping("/list")
    public ResponseEntity<List<VehiculeServiceDto>> listVehiculeService() {
        return ResponseEntity.status(200).body(vehiculeServiceService.listVehiculeService(0, 5));
    }

    @Secured("ADMINISTRATEUR")
    @GetMapping("/delete")
    public ResponseEntity<List<VehiculeServiceDto>> deleteVehiculeService(@RequestParam Integer id) {

        // Suppression des reservations liées au véhicule de service supprimé
        LocalDateTime date = LocalDateTime.now();
        reservationVehiculeServiceService.adminDeleteAllReservationsByVehiculeServiceId(id, date);
        //

        vehiculeServiceService.deleteVehiculeService(id);
        return ResponseEntity.status(200).body(vehiculeServiceService.listVehiculeService(0, 5));
    }

    @PostMapping("/modify")
    public ResponseEntity<List<VehiculeServiceDto>> modifyVehiculeService(@Validated @RequestBody VehiculeServiceDto dto) {
        vehiculeServiceService.modifyVehiclueService(dto);
        return ResponseEntity.status(200).body(vehiculeServiceService.listVehiculeService(0, 5));
    }
}
