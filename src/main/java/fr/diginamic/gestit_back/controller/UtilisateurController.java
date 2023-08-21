package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.UtilisateurDto;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.repository.UtilisateurRepository;
import fr.diginamic.gestit_back.service.UtilisateurService;
import fr.diginamic.gestit_back.utils.JWTUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
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
    private JWTUtils jwtUtils;

    @GetMapping("/findNom")
    public List<Utilisateur> findNom (){
        return this.utilisateurCollaborateurService.listerUtilisateurParNom("Admin1");
    }
    @PostMapping("/create2")
    public ResponseEntity createUser(@RequestBody Utilisateur utilisateur){
        System.out.println(utilisateur);
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        utilisateurRepository.save(utilisateur);
        return ResponseEntity.status(200).body(utilisateur);
    }

    @PostMapping("/create")
    public Utilisateur nouveauUtilisateur (@RequestBody @Valid UtilisateurDto utilisateurDto){
        return this.utilisateurCollaborateurService.creerUtilisateur(utilisateurDto);
    }

    @PostMapping("/modify")
    public void utilisateurModifie (@RequestBody @Valid UtilisateurDto nouveauUtilisateurDto, @RequestParam Integer idUser){
        this.utilisateurCollaborateurService.modifierUtilisateur(nouveauUtilisateurDto,idUser);

    }

    @GetMapping("/disable")
    public void utilisateurDesactive(@RequestParam Integer idUser){
        this.utilisateurCollaborateurService.desactiverUtilisateur(idUser);
    }

}
