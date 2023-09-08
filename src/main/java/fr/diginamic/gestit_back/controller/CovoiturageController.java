package fr.diginamic.gestit_back.controller;
import fr.diginamic.gestit_back.dto.CovoiturageDto;
import fr.diginamic.gestit_back.exceptions.CovoiturageNotFoundException;
import fr.diginamic.gestit_back.service.CovoiturageService;


import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.modelmapper.ModelMapper;
import java.util.List;

import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Adresse;
import fr.diginamic.gestit_back.entites.Commune;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculePerso;
import fr.diginamic.gestit_back.entites.Modele;
import fr.diginamic.gestit_back.entites.Marque;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
// @Secured("COLLABORATEUR")
@Data
@RequestMapping(EndPointsApp.COVOITURAGE_ENDPOINT)
public class CovoiturageController {

    @Autowired
    private CovoiturageService covoiturageService;

    private ObjectMapper convertisseurJavaJson = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .enable(SerializationFeature.INDENT_OUTPUT)
            .build();

    private ModelMapper modelMapper;

    protected CovoiturageController(CovoiturageService covoiturageService, ModelMapper mapper) {
        this.covoiturageService = covoiturageService;
        this.modelMapper = mapper;
    }

    @PostMapping(EndPointsApp.COVOITURAGE_CREATE_RESOURCE)
    public ResponseEntity<String> createCovoiturage(RequestEntity<Covoiturage> covoiturage)
            throws JsonProcessingException {
        Covoiturage savedCovoiturage = covoiturageService.add(covoiturage.getBody());
        String covoiturageToSend = this.convertisseurJavaJson.writeValueAsString(savedCovoiturage);
        return ResponseEntity.status(HttpStatus.CREATED).body(covoiturageToSend);
    }

    @GetMapping(EndPointsApp.COVOITURAGE_GET_ALL_RESOURCE)
    public List<Covoiturage> getCovoiturages() {
        return covoiturageService.list();
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody @Valid Covoiturage covoiturage) {
        try {
            covoiturage.setId(id);
            Covoiturage updatedCovoiturage = covoiturageService.update(covoiturage);
            return ResponseEntity.ok(entity2Dto(updatedCovoiturage));
        } catch (CovoiturageNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            covoiturageService.delete(id);
            return ResponseEntity.noContent().build();

        } catch (CovoiturageNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    private CovoiturageDto entity2Dto(Covoiturage entity) {
        return modelMapper.map(entity, CovoiturageDto.class);
    }

    private List<CovoiturageDto> list2Dto(List<Covoiturage> listCovoiturages) {
        return listCovoiturages.stream().map(
                        entity -> entity2Dto(entity))
                .collect(Collectors.toList());
    }


    /* Fonctions utilitaires */
    public static Covoiturage Covoiturage_instanceExample() {
        Covoiturage covoiturage = new Covoiturage();
        Commune commune = new Commune("Paris", 75000);
        Adresse adresseDepart = new Adresse(26, "rue des Alouettes", commune);
        Adresse adresseArrivee = new Adresse(32, "Bvd des Aubépines", commune);

        Utilisateur conducteur = new Utilisateur();
        LocalDate dateConducteur = LocalDate.of(2022, 4, 6);
        List<Covoiturage> conducteurCovoituragesOrganises = new ArrayList<>();
        List<Covoiturage> conducteurCovoituragesPassagers = new ArrayList<>();
        List<String> roleConducteur = new ArrayList<>();
        roleConducteur.add("COLLABORATEUR");
        conducteur.setEmail("RonaldMerziner@gmail.com");
        conducteur.setMotDePasse("4321");
        conducteur.setNom("Merziner");
        conducteur.setCovoituragesOrganises(conducteurCovoituragesOrganises);
        conducteur.setCovoituragesPassagers(conducteurCovoituragesPassagers);
        conducteur.setDateNonValide(dateConducteur);
        conducteur.setRoles(roleConducteur);

        Utilisateur passager = new Utilisateur();
        LocalDate datePassager = LocalDate.of(2020, 1, 8);
        List<Covoiturage> covoituragesOrganises = new ArrayList<>();
        List<Covoiturage> covoituragesPassagers = new ArrayList<>();
        List<String> rolePassager = new ArrayList<>();
        rolePassager.add("COLLABORATEUR");
        passager.setEmail("RonaldMerziner@gmail.com");
        passager.setMotDePasse("4321");
        passager.setNom("Merziner");
        passager.setCovoituragesOrganises(covoituragesOrganises);
        passager.setCovoituragesPassagers(covoituragesPassagers);
        passager.setDateNonValide(datePassager);
        passager.setRoles(rolePassager);
        List<Utilisateur> passagersABord = new ArrayList<>();

        /* A CORRIGER : CET APPEL POSE PROBLEME */
        // passagersABord.add(conducteur);

        VehiculePerso vehiculeConducteur = new VehiculePerso();
        vehiculeConducteur.setImmatriculation("789-hu-78");
        Modele modele = new Modele();
        Marque marque = new Marque();
        marque.setNom("Fiat");
        modele.setNom("mini500");
        modele.setMarque(marque);
        vehiculeConducteur.setModele(modele);
        vehiculeConducteur.setNombreDePlaceDisponibles(4);
        vehiculeConducteur.setProprietaire(conducteur);

        covoiturage.setDistanceKm(15);
        covoiturage.setDureeTrajet(30);
        covoiturage.setNombrePlacesRestantes(4);
        covoiturage.setAdresseDepart(adresseDepart);
        covoiturage.setAdresseArrivee(adresseArrivee);
        covoiturage.setOrganisateur(conducteur);
        covoiturage.setId(23);
        covoiturage.setVehiculePerso(vehiculeConducteur);
        covoiturage.setPassagers(passagersABord);

        return covoiturage;
    }
}