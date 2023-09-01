package fr.diginamic.gestit_back.divers;

import fr.diginamic.gestit_back.service.CovoiturageService;

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
import fr.diginamic.gestit_back.controller.EndPointsApp;

@RestController
// @Secured("COLLABORATEUR")
@Data
@RequestMapping(EndPointsApp.COVOITURAGE_ENDPOINT)
public class mecanismeRestTemplateController {

    @Autowired
    private CovoiturageService covoiturageService;

    private ObjectMapper convertisseurJavaJson = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .enable(SerializationFeature.INDENT_OUTPUT)
            .build();

    private ModelMapper modelMapper;

    protected mecanismeRestTemplateController(CovoiturageService covoiturageService, ModelMapper mapper) {
        this.covoiturageService = covoiturageService;
        this.modelMapper = mapper;
    }

    @PostMapping(EndPointsApp.TEST_COVOITURAGE_CREATE_RESOURCE)
    // public Covoiturage createCovoiturage(@RequestBody Covoiturage covoiturage){
    // throws JsonProcessingException {
    public ResponseEntity<String> createCovoiturage(RequestEntity<Covoiturage> covoiturage)
            throws JsonProcessingException {
        // Covoiturage savedCovoiturage = covoiturageService.add(covoiturage.getBody());
        String covoiturageToSend = this.convertisseurJavaJson.writeValueAsString(covoiturage.getBody());
        return ResponseEntity.status(HttpStatus.CREATED).body(covoiturageToSend);
    }

    @GetMapping(EndPointsApp.TEST_COVOITURAGE_GET_ALL_RESOURCE)
    public List<Covoiturage> getCovoiturages() {
        return covoiturageService.list();
    }
}