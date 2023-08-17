package fr.diginamic.gestit_back.dto;

import java.util.HashSet;
import java.util.Set;

import fr.diginamic.gestit_back.entites.Adresse;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculePerso;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class CovoiturageDto {
    private Integer id;
    private Integer nombrePlacesRestantes;
    private Integer dureeTrajet;
    private Integer distanceKm;

    private AdresseDto adresseDepart;
    private AdresseDto adresseArrivee;

    private UtilisateurDto organisateur;
    private Set<UtilisateurDto> passagers = new HashSet<>();

    private VehiculePersoDto vehiculePerso;
}
