package fr.diginamic.gestit_back.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class VehiculePerso extends AbstractVehicule {

    @ManyToOne
    private Organisateur proprietaire;

    public VehiculePerso() {

    }
}
