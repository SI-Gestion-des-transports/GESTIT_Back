package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.service.CovoiturageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
@AllArgsConstructor
@Secured("COLLABORATEUR")
@RequestMapping("/covoiturages")
public class CovoiturageController {

    private CovoiturageService covoiturageService;

    @GetMapping
    public ResponseEntity<List<Covoiturage>> lister() {
        List<Covoiturage> covoiturages = this.getCovoiturageService().listerCovoiturages();
        return ResponseEntity.status(HttpStatus.OK).body(covoiturages);
    }
}
