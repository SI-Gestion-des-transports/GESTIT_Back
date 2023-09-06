package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.enumerations.Categorie;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
//@Secured("ADMINISTRATEUR")
@RequestMapping("/categorie")
@CrossOrigin
public class CategorieController {
    @GetMapping
    public ResponseEntity<List<String>> getCategories(){
        return ResponseEntity.status(200).body(Arrays.stream(Categorie.values()).map(Categorie::name).toList());
    }
}
