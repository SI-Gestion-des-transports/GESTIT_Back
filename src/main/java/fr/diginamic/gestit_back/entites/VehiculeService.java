package fr.diginamic.gestit_back.entites;

import fr.diginamic.gestit_back.enumerations.Categorie;
import fr.diginamic.gestit_back.enumerations.Motorisation;
import fr.diginamic.gestit_back.enumerations.Statut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
public class VehiculeService extends AbstractVehicule {

    @Column(name="photo_url")
    private String photoURL;

    @Column(name="emission_co2")
    private Double emissionCO2;

    @Enumerated(EnumType.STRING)
    private Motorisation motorisation;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    @Enumerated(EnumType.STRING)
    private Categorie categorie;

    @OneToMany(mappedBy = "vehiculeService")
    private Set<ReservationVehiculeService> reservationVehiculeServices = new HashSet<>();
    public VehiculeService() {

    }
}
