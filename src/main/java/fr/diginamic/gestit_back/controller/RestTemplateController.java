package fr.diginamic.gestit_back.controller;

import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.service.RestTemplateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/RestTemplate")
public class RestTemplateController {

    @Autowired
    private RestTemplateService restTemplateService;

    @GetMapping("/getAllCovoiturages")
    public ResponseEntity<String> getAllCovoiturages() {
        return restTemplateService.allCovoiturages();

    }   

    @PostMapping("/addCovoiturage")
    public ResponseEntity<Covoiturage> createCovoiturage(@RequestBody Covoiturage covoiturage) {
        return restTemplateService.createCovoiturage(covoiturage);

    }



}
