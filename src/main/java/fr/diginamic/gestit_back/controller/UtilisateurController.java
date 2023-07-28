package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.service.UtilisateurService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("utilisateur")
public class UtilisateurController {
    private UtilisateurService utilisateurCollaborateurService;

    @GetMapping("/findNom")
    public List<Utilisateur> findNom (){
        return this.utilisateurCollaborateurService.listerUtilisateurParNom("Admin1");
    }


}
