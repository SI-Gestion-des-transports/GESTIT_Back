package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.MarqueDto;
import fr.diginamic.gestit_back.entites.Marque;
import fr.diginamic.gestit_back.service.MarqueService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@Secured({"COLLABORATEUR", "ADMINISTRATEUR"})
@Data
@AllArgsConstructor
@RequestMapping("marque")
@Validated
@CrossOrigin
public class MarqueController {

    private MarqueService marqueService;

    @PostMapping("/create")
    public void creerMarque(@Valid @RequestBody MarqueDto marqueDto) {
        if (marqueService.verifyMarque(marqueDto.nom())) throw new RuntimeException("la marque existe déjà");
        else this.marqueService.creerMarque(marqueDto.nom());
    }

    @GetMapping("/list")
    public ResponseEntity listerMarque() {
        List<String> list = new ArrayList<>();
        for (Marque m : this.marqueService.listerMarques()) {
            list.add(m.getNom());
        }
        return ResponseEntity.status(200).body(list);
    }

    @PostMapping("/delete")
    public void deleteMarque(@Valid @RequestBody MarqueDto marqueDto) {
        marqueService.deleteMarqueByNom(marqueDto.nom());
    }

    @PostMapping("/update")
    public void updateMarque(@Valid @RequestBody MarqueDto marqueDto) {
        marqueService.updateMarque(marqueDto);
    }
}
