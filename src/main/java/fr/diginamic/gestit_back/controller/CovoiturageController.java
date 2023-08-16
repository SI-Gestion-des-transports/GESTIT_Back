package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.service.CovoiturageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/covoiturages")
public class CovoiturageController {

    private CovoiturageService covoiturageService;

    @GetMapping
    public ResponseEntity<List<Covoiturage>> lister() {
        List<Covoiturage> covoiturages;
        try {
            covoiturages = this.getCovoiturageService().listerCovoiturages();

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        if (covoiturages.size() == 0)
            return ResponseEntity.notFound().build();
        return ResponseEntity.status(HttpStatus.OK).body(covoiturages);
    }

}
