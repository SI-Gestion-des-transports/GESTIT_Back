package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.VehiculePersoDto;
import fr.diginamic.gestit_back.entites.Modele;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculePerso;
import fr.diginamic.gestit_back.enumerations.StatutPerso;
import fr.diginamic.gestit_back.repository.ModeleRepository;
import fr.diginamic.gestit_back.repository.UtilisateurRepository;
import fr.diginamic.gestit_back.repository.VehiculePersoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class VehiculePersoService {
    private VehiculePersoRepository vehiculePersoRepository;
    private UtilisateurRepository utilisateurRepository;
    private ModeleRepository modeleRepository;
    private UtilisateurService utilisateurService;


    public List<VehiculePersoDto> listVehiculePersoByUserId(Integer userId) {
        Utilisateur utilisateur = utilisateurService.trouverParId(userId);
        List<VehiculePerso> list = vehiculePersoRepository.findVehiculePersoByProprietaire(utilisateur);
        return list.stream().filter(vp->vp.getStatut()!=StatutPerso.SUPPRIMER).map(VehiculePersoDto::new).toList();
    }


    public void createVehiculePerso(VehiculePersoDto dto, Integer userId) {
        //VehiculePerso vehiculePerso = vehiculePersoRepository.findVehiculePersoByImmatriculation(dto.getImmatriculation());
        // if (vehiculePerso == null) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(userId);

        Modele modele = modeleRepository.findModeleByNom(dto.getModele());

        utilisateur.ifPresent(user -> vehiculePersoRepository.save(new VehiculePerso(dto, user, modele)));
        // }else throw new RuntimeException("Immatriculation existait !");
    }

    public void deleteVehiculePerso(Integer id) {

        vehiculePersoRepository.findById(id).get().setStatut(StatutPerso.SUPPRIMER);
    }

    public void modifyVehiculePerso(VehiculePersoDto dto, Integer userId) {
        createVehiculePerso(dto, userId);
    }

    public VehiculePerso findVehiculePersoById(Integer id) {
        return vehiculePersoRepository.findById(id).get();
    }
}
