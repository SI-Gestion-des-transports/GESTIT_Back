package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.repository.UtilisateurRepository;
import fr.diginamic.gestit_back.service.UtilisateurService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("utilisateur")
public class UtilisateurController {
    private UtilisateurService utilisateurCollaborateurService;
    private PasswordEncoder passwordEncoder;
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/findNom")
    public List<Utilisateur> findNom (){
        return this.utilisateurCollaborateurService.listerUtilisateurParNom("Admin1");
    }
    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody Utilisateur utilisateur){
        System.out.println(utilisateur);
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        utilisateurRepository.save(utilisateur);
        return ResponseEntity.status(200).body(utilisateur);
    }


}
