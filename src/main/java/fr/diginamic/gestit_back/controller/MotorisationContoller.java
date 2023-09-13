package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.entites.Marque;
import fr.diginamic.gestit_back.entites.Modele;
import fr.diginamic.gestit_back.enumerations.Categorie;
import fr.diginamic.gestit_back.enumerations.Motorisation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
//@Secured("ADMINISTRATEUR")
@RequestMapping("/motorisation")

public class MotorisationContoller {
    @GetMapping
    public ResponseEntity<List<String>> getMotorisation(){

        return ResponseEntity.status(200).body(Arrays.stream(Motorisation.values()).map(Motorisation::name).toList());
    }
}
