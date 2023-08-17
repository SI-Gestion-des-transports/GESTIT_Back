package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.UtilisateurDto;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.repository.UtilisateurRepository;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Data
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> listerUtilisateurParNom (String nom){
        List<Utilisateur> utilisateurs = new ArrayList<>();
          /* Optional<> option 1: .get()
          if (this.utilisateurRepository.findByNom(nom).isPresent()){
                    Utilisateur utilisateur = utilisateurRepository.findByNom(nom).get();
                    utilisateurs.add(utilisateur);
                }*/

        /* Optional<> option 2: orElse*/
        Utilisateur u = this.utilisateurRepository.findByNom(nom).orElseThrow();
        utilisateurs.add(u);
        return utilisateurs;
    }

    public Utilisateur trouverParId(Integer id){
        return this.utilisateurRepository.findById(id).orElseThrow();
    }



    public Utilisateur creerUtilisateur (UtilisateurDto utilisateurDto){
        if (utilisateurRepository.findByEmail(utilisateurDto.email()).isPresent()){
            throw new RuntimeException("L'email a été utilisé par un autre utilisateur");
        } else {
            return this.utilisateurRepository.save(new Utilisateur(utilisateurDto.nom(), utilisateurDto.email(), utilisateurDto.motDePasse(), utilisateurDto.roles()));
        }
    }


    public Utilisateur modifierUtilisateur (UtilisateurDto nouveauUtilisateur, Integer idUtilisateur){
        Utilisateur utilisateurModif = utilisateurRepository.findById(idUtilisateur).orElseThrow();
        utilisateurModif.setNom(nouveauUtilisateur.nom());
        utilisateurModif.setMotDePasse(nouveauUtilisateur.motDePasse());
        utilisateurModif.setRoles(nouveauUtilisateur.roles());

        return this.utilisateurRepository.save(utilisateurModif);
    }

    public void supprimerUtilisateur (Integer idUtilisateur){
        utilisateurRepository.deleteById(idUtilisateur);
    }


}
