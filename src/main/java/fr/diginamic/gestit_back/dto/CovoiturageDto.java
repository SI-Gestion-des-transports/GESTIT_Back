package fr.diginamic.gestit_back.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class CovoiturageDto {
    private Integer id;
    private Integer nombrePlacesRestantes;
    private Integer dureeTrajet;
    private Integer distanceKm;
    private LocalDate dateDepart;

    private AdresseDto adresseDepart;
    private AdresseDto adresseArrivee;

    private UtilisateurDto organisateur;
    private List<UtilisateurDto> passagers = new ArrayList<>();

    private VehiculePersoDto vehiculePerso;
}
