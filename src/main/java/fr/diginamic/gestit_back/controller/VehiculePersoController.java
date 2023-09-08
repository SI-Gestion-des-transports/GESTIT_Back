package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.configuration.JWTConfig;
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
//@Secured("COLLABORATEUR")

@AllArgsConstructor
public class VehiculePersoController {
    private VehiculePersoService vehiculePersoService;
    private JWTUtils jwtUtils;
    private CovoiturageService covoiturageService;
    private JWTConfig jwtConfig;


    @PostMapping("/create")
    public ResponseEntity<List<VehiculePersoDto>> createVehiculePerso(@RequestHeader HttpHeaders httpHeaders,
                                                                      @Validated @RequestBody VehiculePersoDto dto){
        Integer userId = Integer.decode(jwtUtils.parseJWT(httpHeaders.get(jwtConfig.getName()).get(0)).getSubject());
        System.out.println(userId);
        vehiculePersoService.createVehiculePerso(dto,userId);
        return ResponseEntity.status(200).build();
    }
    @DeleteMapping("/delete")
    public ResponseEntity<List<VehiculePersoDto>> deleteVehiculePerso(
            @RequestParam Integer id,
            //@RequestParam Integer userId,
            @RequestHeader HttpHeaders httpHeaders
    ) throws CovoiturageNotFoundException {
        Integer userId = Integer.decode(jwtUtils.parseJWT(httpHeaders.get(jwtConfig.getName()).get(0)).getSubject());

        List<Covoiturage> covoiturages = covoiturageService.findCovoituragesByVehiculePerSupprimer(vehiculePersoService.findVehiculePersoById(id));

        for (Covoiturage c:covoiturages
             ) {

            covoiturageService.delete(c.getId());
        }
        vehiculePersoService.deleteVehiculePerso(id);
        return  ResponseEntity.status(200).body(vehiculePersoService.listVehiculePersoByUserId(userId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<VehiculePersoDto>> listVehiculePerso(
            @RequestHeader HttpHeaders httpHeaders
    ){
        System.out.println(httpHeaders.get(jwtConfig.getName()).get(0));
        Integer userId = Integer.decode(jwtUtils.parseJWT(httpHeaders.get(jwtConfig.getName()).get(0)).getSubject());
        return  ResponseEntity.status(200).body(vehiculePersoService.listVehiculePersoByUserId(userId));
    }
    @PutMapping("/modify")
    public ResponseEntity modifyVehiculePerso(@RequestHeader HttpHeaders httpHeaders,@Validated @RequestBody VehiculePersoDto dto){
        Integer userId = Integer.decode(jwtUtils.parseJWT(httpHeaders.get(jwtConfig.getName()).get(0)).getSubject());
        vehiculePersoService.modifyVehiculePerso(dto,userId);
        return  ResponseEntity.status(200).build();
    }


    @GetMapping("/findById")
    public ResponseEntity<VehiculePersoDto> findById(@RequestParam Integer id){
        return ResponseEntity.status(200).body(new VehiculePersoDto(vehiculePersoService.findVehiculePersoById(id)));

    }

}
