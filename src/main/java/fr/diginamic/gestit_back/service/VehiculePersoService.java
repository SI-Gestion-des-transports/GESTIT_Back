package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.VehiculePersoDto;
import fr.diginamic.gestit_back.entites.Modele;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculePerso;
import fr.diginamic.gestit_back.repository.ModeleRepository;
import fr.diginamic.gestit_back.repository.UtilisateurRepository;
import fr.diginamic.gestit_back.repository.VehiculePersoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class VehiculePersoService {
    private VehiculePersoRepository vehiculePersoRepository;
    private UtilisateurRepository utilisateurRepository;
    private ModeleRepository modeleRepository;

    public void createVehiculePerso(VehiculePersoDto dto) {
        VehiculePerso vehiculePerso = vehiculePersoRepository.findVehiculePersoByImmatriculation(dto.getImmatriculation());
        if (vehiculePerso == null) {
            Optional<Utilisateur> utilisateur = utilisateurRepository.findById(dto.getUserId());
            Modele modele = modeleRepository.findModeleByNom(dto.getModele());
            vehiculePersoRepository.save(new VehiculePerso(dto,utilisateur.get(),modele));
        }
    }
}
