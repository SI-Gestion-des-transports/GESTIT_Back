package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.MessageDto;
import fr.diginamic.gestit_back.dto.UtilisateurDto;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.repository.UtilisateurRepository;
import fr.diginamic.gestit_back.utils.NotFoundOrValidException;
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
            if (this.utilisateurRepository.findByNom(nom).isPresent()){
                    Utilisateur utilisateur = utilisateurRepository.findByNom(nom).get();
                    utilisateurs.add(utilisateur);
                }
        return utilisateurs;
    }

    public Utilisateur trouverParId(Integer id){
        return this.utilisateurRepository.findById(id).orElseThrow(()-> new NotFoundOrValidException(new MessageDto("Cet utilisateur n'existe pas")));
    }


    public void creerUtilisateurOld (UtilisateurDto utilisateurDto) {
        Utilisateur nouveauUtilisateur = new Utilisateur(utilisateurDto.nom(), utilisateurDto.email(), utilisateurDto.motDePasse(), utilisateurDto.roles());
    }

    public void creerUtilisateur (UtilisateurDto utilisateurDto){
        if (utilisateurRepository.findByEmail(utilisateurDto.email()).isPresent()){
            throw new RuntimeException("le mail a été utilisé par un autre utilisateur");
        } else {
            this.utilisateurRepository.save(new Utilisateur(utilisateurDto.nom(), utilisateurDto.email(), utilisateurDto.motDePasse(), utilisateurDto.roles()));
        }

    }

}
