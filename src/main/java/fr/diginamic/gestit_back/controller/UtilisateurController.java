package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.MessageDto;
import fr.diginamic.gestit_back.dto.UtilisateurDto;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.repository.UtilisateurRepository;
import fr.diginamic.gestit_back.service.UtilisateurService;
import fr.diginamic.gestit_back.utils.JWTUtils;
import fr.diginamic.gestit_back.utils.NotFoundOrValidException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("utilisateur")
public class UtilisateurController {
    private UtilisateurService utilisateurCollaborateurService;
    private PasswordEncoder passwordEncoder;

    private JWTUtils jwtUtils;

    @GetMapping("/findNom")
    public List<Utilisateur> findNom() {
        return this.utilisateurCollaborateurService.listerUtilisateurParNom("Admin1");
    }
/*    @PostMapping("/create2")
    public ResponseEntity createUser(@RequestBody Utilisateur utilisateur){
        System.out.println(utilisateur);
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        utilisateurRepository.save(utilisateur);
        return ResponseEntity.status(200).body(utilisateur);
    }*/

    @PostMapping("/create")
    public Utilisateur nouveauUtilisateur(@RequestBody @Valid UtilisateurDto utilisateurDto) {
        return this.utilisateurCollaborateurService.creerUtilisateur(utilisateurDto);
    }

    @PostMapping("/modify")
    public ResponseEntity<Utilisateur> utilisateurModifie(@RequestHeader HttpHeaders httpHeaders, @RequestBody @Valid UtilisateurDto nouveauUtilisateurDto, @RequestParam Integer idUser) {
        Integer utilisateurConnecteId = Integer.decode(jwtUtils.parseJWT(httpHeaders.get("JWT-TOKEN").get(0)).getSubject());

        Optional<Utilisateur> utilisateur = this.utilisateurCollaborateurService.rechercheParId(utilisateurConnecteId);
        if (utilisateur.isPresent() && utilisateurConnecteId.equals(idUser)) {
            return ResponseEntity.status(200).body(this.utilisateurCollaborateurService.modifierUtilisateur(nouveauUtilisateurDto, idUser));
        } else {
            throw new NotFoundOrValidException(new MessageDto("La modification n'est pas possible"));
        }
    }

    @GetMapping("/disable")
    public void utilisateurDesactive(@RequestHeader HttpHeaders httpHeaders, @RequestParam Integer idUser) {
        Integer utilisateurConnecteId = Integer.decode(jwtUtils.parseJWT(httpHeaders.get("JWT-TOKEN").get(0)).getSubject());
        LocalDate dateDesactivation = LocalDate.now();
        Optional<Utilisateur> utilisateur = this.utilisateurCollaborateurService.rechercheParId(utilisateurConnecteId);
        if (utilisateur.isPresent() && utilisateurConnecteId.equals(idUser)) {
            this.utilisateurCollaborateurService.desactiverUtilisateur(idUser, dateDesactivation);
        } else {
            throw new NotFoundOrValidException(new MessageDto("Vous ne pouvez pas désactiver cet utilisateur"));

        }

    }
}
