package fr.diginamic.gestit_back.entites;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
public class Covoiturage extends AbstractBaseEntity {

    //@NotNull(message = "nombreDePlacesrestantes must not be null")
    private Integer nombrePlacesRestantes;

    private Integer dureeTrajet;
    private Integer distanceKm;
    private LocalDate dateDepart;

    @ManyToOne
    private Adresse adresseDepart;

    @ManyToOne
    private Adresse adresseArrivee;

    //@ManyToMany(mappedBy = "covoituragesPassagers")
    @ManyToMany
    @JoinTable(name = "covoiturages_collaborateur",
            joinColumns = @JoinColumn(name = "covoiturage_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "collaborateur_id", referencedColumnName = "id"))
    private List<Utilisateur> passagers = new ArrayList<>();

    @ManyToOne
    private Utilisateur organisateur;

    @ManyToOne
    private VehiculePerso vehiculePerso;

    public Covoiturage() {

    }

    public Covoiturage(Integer nombreDePlacesRestantes) {
        this.nombrePlacesRestantes = nombreDePlacesRestantes;

    }

}
