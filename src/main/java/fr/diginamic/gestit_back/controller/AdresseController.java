package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.AdresseDto;
import fr.diginamic.gestit_back.entites.Commune;
import fr.diginamic.gestit_back.service.AdresseService;
import fr.diginamic.gestit_back.service.CommuneService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@Secured("COLLABORATEUR")
@AllArgsConstructor
@RequestMapping("adresse")
public class AdresseController {

    private AdresseService adresseService;
    private CommuneService communeService;

    @PostMapping
    public void creerAdresse(@RequestBody AdresseDto adresseDto) {
        this.adresseService.nouvelleAdresse(adresseDto);
    }

    @GetMapping("/find")
    public Commune find() {
        return this.communeService.verifierExistenceCommune("Montpellier", 34000);
    }
}
