package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.UtilisateurDto;
import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.repository.CovoiturageRepository;
import fr.diginamic.gestit_back.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
@Data
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final CovoiturageRepository covoiturageRepository;

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

    public Optional<Utilisateur> rechercheParId(Integer id){
        return this.utilisateurRepository.findById(id);
    }



    public Utilisateur creerUtilisateur (UtilisateurDto utilisateurDto){
        if (utilisateurRepository.findByEmail(utilisateurDto.email()).isPresent()){
            throw new RuntimeException("L'email a été utilisé par un autre utilisateur");
        } else {
            return this.utilisateurRepository.save(new Utilisateur(utilisateurDto.nom(), utilisateurDto.email(), utilisateurDto.motDePasse(), utilisateurDto.roles(), utilisateurDto.dateNonValide()));
        }
    }


    public Utilisateur modifierUtilisateur (UtilisateurDto nouveauUtilisateur, Integer idUtilisateur){
        Utilisateur utilisateurModif = utilisateurRepository.findById(idUtilisateur).orElseThrow();
        utilisateurModif.setNom(nouveauUtilisateur.nom());
        utilisateurModif.setMotDePasse(nouveauUtilisateur.motDePasse());
        utilisateurModif.setRoles(nouveauUtilisateur.roles());
        utilisateurModif.setDateNonValide(nouveauUtilisateur.dateNonValide());
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //LocalDate date = LocalDate.parse(nouveauUtilisateur.dateNonValide(), formatter);
        //utilisateurModif.setDateNonValide(date);

        return this.utilisateurRepository.save(utilisateurModif);
    }


    public void desactiverUtilisateur(Integer idUtilisateur) {
            // Récupérer l'utilisateur à désactiver
            Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(idUtilisateur);
            // Si utulisateur est présent, prendre la date à partir de laquelle l'utilisateur n'est plus active.


            if (utilisateurOptional.isPresent()) {
                Utilisateur utilisateur = utilisateurOptional.get();
                LocalDate dateNonValide = utilisateur.getDateNonValide();
                // Si la dateNonValide (la date à partir de laquelle l'utilisateur n'est plus active) est présente, cherche les covoiturages organisés par cet utilisateur
                if (dateNonValide != null){
                    /*
                    @OneToMany(mappedBy = "organisateur")
                        private Set<Covoiturage> covoituragesOrganises = new HashSet<>();
                         => l'entity Utilisateur n'a pas accès sur les données de covoiturages, la fonction : "utilisateur.getCovoituragesOrganises()" ne marche pas; il faut passer par la classe Covoiturage (covoiturageRepository.findCovoituragesByOrganisateur(utilisateur) )
                        */
                    Set<Covoiturage> covoiturageOrganise = covoiturageRepository.findCovoituragesByOrganisateur(utilisateur);
                    // Si la date de covoiturage est après la dateNonValide, supprime le covoiturage
                    for(Covoiturage c:covoiturageOrganise) {
                        if (c.getDateDepart().isAfter(dateNonValide)) {
                            covoiturageRepository.delete(c);
                        }
                    }
            }
        }
    }



}
