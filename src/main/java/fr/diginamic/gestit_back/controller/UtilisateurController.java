package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.dto.MessageDto;
import fr.diginamic.gestit_back.dto.UtilisateurDto;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.exceptions.NotFoundOrValidException;
import fr.diginamic.gestit_back.service.UtilisateurService;
import fr.diginamic.gestit_back.utils.JWTUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@Data
//@Secured({"COLLABORATEUR", "ADMINISTRATEUR"})
@AllArgsConstructor
//@CrossOrigin
@RequestMapping("utilisateur")
public class UtilisateurController {
    private UtilisateurService utilisateurCollaborateurService;
    private PasswordEncoder passwordEncoder;
    private JWTUtils jwtUtils;


    /**
     * Récupère une liste d'objets Utilisateur avec le nom spécifié.
     *
     * @return Une liste d'objets Utilisateur correspondant au nom "Admin1".
     * @see Utilisateur
     */
    @GetMapping("/findNom")
    public List<Utilisateur> findNom() {
        return this.utilisateurCollaborateurService.listerUtilisateurParNom("Admin1");
    }

    /**
     * Récupère une l'tilisateur avec l'id spécifié.
     *
     * @return Une liste d'utilisateur correspondant au nom "Admin1".
     * @see Utilisateur
     */
    @GetMapping("/{userId}")
    public UtilisateurDto findParId(@PathVariable Integer userId) {
        return this.utilisateurCollaborateurService.trouverDtoParId(userId);
    }

    /**
     * Crée un nouveau objet Utilisateur en utilisant les informations fournies dans un objet UtilisateurDto.
     *
     * @param utilisateurDto L'objet UtilisateurDto contenant les informations du nouvel utilisateur.
     * @return L'objet Utilisateur nouvellement créé.
     * @throws IllegalArgumentException Si les données fournies dans utilisateurDto ne sont pas valides ou manquantes.
     * @see UtilisateurDto
     * @see Utilisateur
     */
    @PostMapping("/create")
    public Utilisateur nouveauUtilisateur(@RequestBody @Valid UtilisateurDto utilisateurDto) {
        return this.utilisateurCollaborateurService.creerUtilisateur(utilisateurDto);
    }

    /**
     * Modifie un objet Utilisateur existant en utilisant les informations fournies dans un objet UtilisateurDto.
     *
     * @param httpHeaders      Les en-têtes HTTP de la requête, contenant le token JWT pour l'authentification.
     * @param nouveauUtilisateurDto L'objet UtilisateurDto contenant les nouvelles informations de l'utilisateur.
     * @param idUser           L'identifiant de l'utilisateur à modifier.
     * @return Une ResponseEntity contenant l'objet Utilisateur modifié si l'opération réussit (code 200).
     * @throws NotFoundOrValidException Si l'utilisateur n'existe pas ou si l'opération de modification n'est pas autorisée.
     * @see UtilisateurDto
     * @see Utilisateur
     */
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


    /**
     * Désactive un utilisateur en utilisant son identifiant, marquant ainsi son statut comme désactivé.
     *
     * @param httpHeaders Les en-têtes HTTP de la requête, contenant le token JWT pour l'authentification.
     * @param idUser      L'identifiant de l'utilisateur à désactiver.
     * @throws NotFoundOrValidException Si l'utilisateur n'existe pas ou si l'opération de désactivation n'est pas autorisée.
     * @see Utilisateur
     */
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
