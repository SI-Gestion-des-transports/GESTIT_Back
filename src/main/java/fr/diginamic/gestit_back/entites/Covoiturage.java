package fr.diginamic.gestit_back.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
public class Covoiturage extends AbstractBaseEntity {

    private Integer nombrePlacesRestantes;
    private Integer dureeTrajet;
    private Integer distanceKm;

    @ManyToOne
    private Adresse adresseDepart;

    @ManyToOne
    private Adresse adresseArrivee;

    @ManyToMany(mappedBy = "covoituragesPassagers")
    private Set<Utilisateur> passagers = new HashSet<>();

    @ManyToOne
    private Utilisateur organisateur;

    @ManyToOne
    private VehiculePerso vehiculePerso;

    public Covoiturage() {

    }
}
