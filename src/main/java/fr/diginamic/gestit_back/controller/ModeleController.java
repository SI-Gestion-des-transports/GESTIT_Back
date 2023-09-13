package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.entites.Marque;
import fr.diginamic.gestit_back.entites.Modele;
import fr.diginamic.gestit_back.service.MarqueService;
import fr.diginamic.gestit_back.service.ModeleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
//@Secured("ADMINISTRATEUR")
@RequestMapping("/modele")

public class ModeleController {
    private MarqueService marqueService;
    private ModeleService modeleService;
    @GetMapping()
    public ResponseEntity<List<String>> getModeleByMarque(@RequestParam String marque){
        Marque seletedMarque = marqueService.getMarqueByName(marque);
        List<Modele> list=modeleService.getModeleByMarque(seletedMarque);

        return ResponseEntity.status(200).body(list.stream().map(Modele::getNom).toList());
    }
}
