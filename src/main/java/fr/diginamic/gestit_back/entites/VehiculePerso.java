package fr.diginamic.gestit_back.entites;

import fr.diginamic.gestit_back.dto.VehiculePersoDto;
import fr.diginamic.gestit_back.enumerations.Categorie;
import fr.diginamic.gestit_back.enumerations.Statut;
import fr.diginamic.gestit_back.enumerations.StatutPerso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
public class VehiculePerso extends AbstractVehicule {

    @ManyToOne
    private Utilisateur proprietaire;

    @OneToMany(mappedBy = "vehiculePerso")
    private List<Covoiturage> covoiturages = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatutPerso statut;

    public VehiculePerso() {
    }

    public VehiculePerso(VehiculePersoDto dto, Utilisateur utilisateur, Modele modele) {
        if (dto.getId() != null) this.setId(dto.getId());
        this.setImmatriculation(dto.getImmatriculation());
        this.setProprietaire(utilisateur);
        this.setModele(modele);
        this.setNombreDePlaceDisponibles(dto.getNombreDePlaceDisponibles());

    }
}
