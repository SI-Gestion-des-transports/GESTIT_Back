package fr.diginamic.gestit_back.entites;

import fr.diginamic.gestit_back.dto.VehiculeServiceDto;
import fr.diginamic.gestit_back.enumerations.Categorie;
import fr.diginamic.gestit_back.enumerations.Motorisation;
import fr.diginamic.gestit_back.enumerations.Statut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private List<ReservationVehiculeService> reservationVehiculeServices = new ArrayList<>();
    public VehiculeService(VehiculeServiceDto dto, Modele modele){
        if (dto.getId()!=null) this.setId(dto.getId());
        this.categorie=dto.getCategorie();
        this.emissionCO2= dto.getEmissionCO2();
        this.statut=dto.getStatut();
        this.motorisation =dto.getMotorisation();
        this.photoURL=dto.getPhotoURL();
        this.setModele(modele);
        this.setImmatriculation(dto.getImmatriculation());
        this.setNombreDePlaceDisponibles(dto.getNombreDePlaceDisponibles());
    }
}
