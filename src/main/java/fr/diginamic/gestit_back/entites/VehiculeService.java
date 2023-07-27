package fr.diginamic.gestit_back.entites;

import fr.diginamic.gestit_back.enumerations.Categorie;
import fr.diginamic.gestit_back.enumerations.Motorisation;
import fr.diginamic.gestit_back.enumerations.Statut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
public class VehiculeService extends AbstractVehicule {

    private String photoURL;

    private Double emissionCO2;

    @Enumerated
    private Motorisation motorisation;

    @Enumerated
    private Statut statut;

    @Enumerated
    private Categorie categorie;

    @OneToMany(mappedBy = "vehiculeService")
    private Set<ReservationVehiculeService> reservationVehiculeServices;
    public VehiculeService() {

    }
}
