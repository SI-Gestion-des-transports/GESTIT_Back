package fr.diginamic.gestit_back.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
public class Organisateur extends Collaborateur{

    @OneToMany(mappedBy = "organisateur")
    private Set<Covoiturage> covoiturages;

    @OneToMany(mappedBy = "proprietaire")
    private Set<VehiculePerso> vehiculesPerso;

    public Organisateur() {

    }
}
