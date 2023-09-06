package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.enumerations.Motorisation;
import fr.diginamic.gestit_back.enumerations.Statut;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
//@Secured("ADMINISTRATEUR")
@RequestMapping("/statusVS")
@CrossOrigin
public class StatusVSController {
    @GetMapping
    public ResponseEntity<List<String>> getStatutVS() {

        return ResponseEntity.status(200).body(Arrays.stream(Statut.values()).map(Statut::name).toList());
    }
}
