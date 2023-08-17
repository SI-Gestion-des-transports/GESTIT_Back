package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.VehiculePersoDto;
import fr.diginamic.gestit_back.service.VehiculePersoService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vehiculeperso")

@AllArgsConstructor
public class VehiculePersoController {
    private VehiculePersoService vehiculePersoService;

    @PostMapping("/create")
    public ResponseEntity<?> createVehiculePerso(@Validated @RequestBody VehiculePersoDto dto) {
        vehiculePersoService.createVehiculePerso(dto);
        return null;
    }

}
