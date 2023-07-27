package fr.diginamic.gestit_back.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

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

    @ManyToMany(mappedBy = "covoiturages")
    private Set<Collaborateur> passagers;

    @ManyToOne
    private Organisateur organisateur;

    public Covoiturage() {

    }
}
