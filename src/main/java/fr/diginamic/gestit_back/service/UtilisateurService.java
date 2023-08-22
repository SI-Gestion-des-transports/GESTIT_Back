package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.UtilisateurDto;
import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.repository.CovoiturageRepository;
import fr.diginamic.gestit_back.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
@Data
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final CovoiturageRepository covoiturageRepository;
    private PasswordEncoder passwordEncoder;


    /**
     * Récupère une liste d'utilisateurs ayant le nom spécifié.
     *
     * @param nom Le nom à rechercher.
     * @return Une liste d'utilisateurs correspondant au nom.
     * @throws NoSuchElementException Si aucun utilisateur avec le nom spécifié n'est trouvé.
     * @see Utilisateur
     */
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


    /**
     * Crée un nouvel utilisateur en utilisant les informations fournies dans un objet UtilisateurDto.
     *
     * @param utilisateurDto L'objet UtilisateurDto contenant les informations du nouvel utilisateur.
     * @return L'objet Utilisateur créé et enregistré dans la base de données.
     * @throws RuntimeException Si l'adresse e-mail fournie dans utilisateurDto est déjà associée à un autre utilisateur.
     * @see UtilisateurDto
     * @see Utilisateur
     */
    public Utilisateur creerUtilisateur (UtilisateurDto utilisateurDto){
        if (utilisateurRepository.findByEmail(utilisateurDto.email()).isPresent()){
            throw new RuntimeException("L'email a été utilisé par un autre utilisateur");
        } else {
            return this.utilisateurRepository.save(new Utilisateur(utilisateurDto.nom(), utilisateurDto.email(), passwordEncoder.encode(utilisateurDto.motDePasse()), Collections.singletonList("COLLABORATEUR")));
        }
    }


    /**
     * Modifie les informations d'un utilisateur existant en utilisant les données fournies dans un objet UtilisateurDto.
     *
     * @param nouveauUtilisateur L'objet UtilisateurDto contenant les nouvelles informations de l'utilisateur.
     * @param idUtilisateur L'identifiant de l'utilisateur à modifier.
     * @return L'objet Utilisateur modifié et enregistré dans la base de données.
     * @throws NoSuchElementException Si aucun utilisateur avec l'identifiant spécifié n'est trouvé.
     * @see UtilisateurDto
     * @see Utilisateur
     */
    @Transactional
    public Utilisateur modifierUtilisateur (UtilisateurDto nouveauUtilisateur, Integer idUtilisateur) {
        Utilisateur utilisateurModif = utilisateurRepository.findById(idUtilisateur).orElseThrow();

            utilisateurModif.setEmail(nouveauUtilisateur.email());
            utilisateurModif.setNom(nouveauUtilisateur.nom());
            utilisateurModif.setMotDePasse(nouveauUtilisateur.motDePasse());
            //utilisateurModif.setDateNonValide(nouveauUtilisateur.dateNonValide());
            return this.utilisateurRepository.save(utilisateurModif);

    }


    /**
     * Désactive un utilisateur en mettant à jour sa date de désactivation et en supprimant les covoiturages organisés
     * par cet utilisateur dont la date de départ est après la date de désactivation.
     *
     * @param idUtilisateur   L'identifiant de l'utilisateur à désactiver.
     * @param dateDesactivation La date à partir de laquelle l'utilisateur ne sera plus actif.
     * @throws NoSuchElementException Si aucun utilisateur avec l'identifiant spécifié n'est trouvé.
     * @see Utilisateur
     * @see Covoiturage
     */
    public void desactiverUtilisateur(Integer idUtilisateur, LocalDate dateDesactivation) {
            // Récupérer l'utilisateur à désactiver
            Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(idUtilisateur);
            // Si utulisateur est présent, prendre la date à partir de laquelle l'utilisateur n'est plus active.
            if (utilisateurOptional.isPresent()) {
                Utilisateur utilisateur = utilisateurOptional.get();
                utilisateur.setDateNonValide(dateDesactivation);

                // Si la dateNonValide (la date à partir de laquelle l'utilisateur n'est plus active) est présente, cherche les covoiturages organisés par cet utilisateur
                //if (dateNonValide != null){
                    /*
                    @OneToMany(mappedBy = "organisateur")
                        private Set<Covoiturage> covoituragesOrganises = new HashSet<>();
                         => l'entity Utilisateur n'a pas accès sur les données de covoiturages, la fonction : "utilisateur.getCovoituragesOrganises()" ne marche pas; il faut passer par la classe Covoiturage (covoiturageRepository.findCovoituragesByOrganisateur(utilisateur) )
                        */

                // Récupérer les covoiturages organisés par cet utilisateur
                Set<Covoiturage> covoiturageOrganise = covoiturageRepository.findCovoituragesByOrganisateur(utilisateur);

                // Supprimer les covoiturages dont la date de départ est après la date de désactivation
                for(Covoiturage c:covoiturageOrganise) {
                    if (c.getDateDepart().isAfter(dateDesactivation)) {
                            covoiturageRepository.delete(c);
                    }
                }
                utilisateurRepository.save(utilisateur);
            } else {
                throw new NoSuchElementException("Utilisateur non trouvé");
            }
    }
}
