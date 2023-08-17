package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.CovoiturageDto;
import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.exceptions.CovoiturageNotFoundException;
import fr.diginamic.gestit_back.service.CovoiturageService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/covoiturages")
public class CovoiturageController {
    private CovoiturageService covoiturageService;
    private ModelMapper modelMapper;

    protected CovoiturageController(CovoiturageService covoiturageService, ModelMapper mapper) {
        this.covoiturageService = covoiturageService;
        this.modelMapper = mapper;
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid Covoiturage covoiturage) {
        Covoiturage persistedCovoiturage = covoiturageService.add(covoiturage);
        CovoiturageDto covoiturageDto = entity2Dto(persistedCovoiturage);

        URI uri = URI.create("/covoiturages/" + covoiturageDto.getId());

        return ResponseEntity.created(uri).body(covoiturageDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer id) {
        try {
            Covoiturage covoiturage = covoiturageService.get(id);
            return ResponseEntity.ok(entity2Dto(covoiturage));

        } catch (CovoiturageNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        List<Covoiturage> listCovoiturages = covoiturageService.list();
        if (listCovoiturages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list2Dto(listCovoiturages));
    }

    /*
     * @GetMapping("/{id}")
     * public ResponseEntity<?> get(@PathVariable("id") Long id) {
     * // ...
     * }
     * 
     * @GetMapping
     * public ResponseEntity<?> list() {
     * // ...
     * }
     * 
     * @PutMapping("/{id}")
     * public ResponseEntity<?> update(...) {
     * // ...
     * }
     * 
     * @DeleteMapping("/{id}")
     * public ResponseEntity<?> delete(...) {
     * // ...
     * }
     */

    private CovoiturageDto entity2Dto(Covoiturage entity) {
        return modelMapper.map(entity, CovoiturageDto.class);
    }

    private List<CovoiturageDto> list2Dto(List<Covoiturage> listCovoiturages) {
        return listCovoiturages.stream().map(
                entity -> entity2Dto(entity))
                .collect(Collectors.toList());
    }
}