package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.VehiculePersoDto;
import fr.diginamic.gestit_back.service.VehiculePersoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vehiculeperso")
@Secured("COLLABORATEUR")
@AllArgsConstructor
public class VehiculePersoController {
    private VehiculePersoService vehiculePersoService;


    @PostMapping("/create")
    public ResponseEntity<List<VehiculePersoDto>> createVehiculePerso(@Validated @RequestBody VehiculePersoDto dto){

        vehiculePersoService.createVehiculePerso(dto);
        return ResponseEntity.status(200).body(vehiculePersoService.listVehiculePersoByUser(dto.getUserId()));
    }
    @GetMapping("/delete")
    public ResponseEntity<List<VehiculePersoDto>> deleteVehiculePerso(
            @RequestParam Integer id,
            @RequestParam Integer userId
    ){
        vehiculePersoService.deleteVehiculePerso(id);
        return  ResponseEntity.status(200).body(vehiculePersoService.listVehiculePersoByUser(userId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<VehiculePersoDto>> listVehiculePerso(
            @RequestParam Integer userId
    ){
        return  ResponseEntity.status(200).body(vehiculePersoService.listVehiculePersoByUser(userId));
    }
    @PostMapping("/modify")
    public ResponseEntity<List<VehiculePersoDto>> modifyVehiculePerso(@Validated @RequestBody VehiculePersoDto dto){

        vehiculePersoService.modifyVehiculePerso(dto);
        return  ResponseEntity.status(200).body(vehiculePersoService.listVehiculePersoByUser(dto.getUserId()));
    }


}
