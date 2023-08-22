package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.VehiculePersoDto;
import fr.diginamic.gestit_back.entites.Commune;
import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.exceptions.CovoiturageNotFoundException;
import fr.diginamic.gestit_back.service.CovoiturageService;
import fr.diginamic.gestit_back.service.VehiculePersoService;
import fr.diginamic.gestit_back.utils.JWTUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("vehiculeperso")
@Secured("COLLABORATEUR")
@AllArgsConstructor
public class VehiculePersoController {
    private VehiculePersoService vehiculePersoService;
    private JWTUtils jwtUtils;
    private CovoiturageService covoiturageService;


    @PostMapping("/create")
    public ResponseEntity<List<VehiculePersoDto>> createVehiculePerso(@Validated @RequestBody VehiculePersoDto dto) {

        vehiculePersoService.createVehiculePerso(dto);
        return ResponseEntity.status(200).body(vehiculePersoService.listVehiculePersoByUserId(dto.getUserId()));
    }

    @GetMapping("/delete")
    public ResponseEntity<List<VehiculePersoDto>> deleteVehiculePerso(
            @RequestParam Integer id,
            //@RequestParam Integer userId,
            @RequestHeader HttpHeaders httpHeaders
    ) throws CovoiturageNotFoundException {
        Integer userId = Integer.decode(jwtUtils.parseJWT(httpHeaders.get("JWT-TOKEN").get(0)).getSubject());

        List<Covoiturage> covoiturages = covoiturageService.findCovoituragesByVehiculePerSupprimer(vehiculePersoService.findVehiculePersoById(id));

        for (Covoiturage c : covoiturages
        ) {

            covoiturageService.delete(c.getId());
        }
        vehiculePersoService.deleteVehiculePerso(id);
        return ResponseEntity.status(200).body(vehiculePersoService.listVehiculePersoByUserId(userId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<VehiculePersoDto>> listVehiculePerso(
            @RequestParam Integer userId
    ) {
        return ResponseEntity.status(200).body(vehiculePersoService.listVehiculePersoByUserId(userId));
    }

    @PostMapping("/modify")
    public ResponseEntity<List<VehiculePersoDto>> modifyVehiculePerso(@Validated @RequestBody VehiculePersoDto dto) {

        vehiculePersoService.modifyVehiculePerso(dto);
        return ResponseEntity.status(200).body(vehiculePersoService.listVehiculePersoByUserId(dto.getUserId()));
    }

    @CrossOrigin
    @PostMapping("/test")
    public ResponseEntity test(@RequestHeader HttpHeaders httpHeaders, @RequestBody String username) {
        System.out.println(httpHeaders.get("username").get(0));
        System.out.println(username);
        return ResponseEntity.status(200).body(new Commune("d", 222));
    }

}
