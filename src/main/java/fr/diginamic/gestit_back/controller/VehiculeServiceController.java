package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.VehiculeServiceDto;
import fr.diginamic.gestit_back.entites.VehiculeService;
import fr.diginamic.gestit_back.service.VehiculeServiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Secured("ADMINISTRATEUR")
@RequestMapping("admin/vehiculeservice")
public class VehiculeServiceController {
    private VehiculeServiceService vehiculeServiceService;

    @PostMapping("/create")
    public ResponseEntity<List<VehiculeServiceDto>> createVehiculeService(@Validated @RequestBody VehiculeServiceDto dto){
        vehiculeServiceService.createVehiculeService(dto);
        return ResponseEntity.status(200).body(vehiculeServiceService.listVehiculeService(0,5));
    }
    @GetMapping("/list")
    public ResponseEntity<List<VehiculeServiceDto>> listVehiculeService(){
        return ResponseEntity.status(200).body(vehiculeServiceService.listVehiculeService(0,5));
    }
    @GetMapping("/delete")
    public ResponseEntity<List<VehiculeServiceDto>> deleteVehiculeService(@RequestParam Integer id){
        vehiculeServiceService.deleteVehiculeService(id);
        return ResponseEntity.status(200).body(vehiculeServiceService.listVehiculeService(0,5));
    }
    @PostMapping("/modify")
    public ResponseEntity<List<VehiculeServiceDto>> modifyVehiculeService (@Validated @RequestBody VehiculeServiceDto dto){
        vehiculeServiceService.modifyVehiclueService(dto);
        return ResponseEntity.status(200).body(vehiculeServiceService.listVehiculeService(0,5));
    }
}
