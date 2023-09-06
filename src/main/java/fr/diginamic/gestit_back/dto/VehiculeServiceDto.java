package fr.diginamic.gestit_back.dto;

import fr.diginamic.gestit_back.entites.VehiculeService;
import fr.diginamic.gestit_back.enumerations.Categorie;
import fr.diginamic.gestit_back.enumerations.Motorisation;
import fr.diginamic.gestit_back.enumerations.Statut;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehiculeServiceDto {
    private Integer id;
    @NotNull
    private String modele;
    @NotNull
    private Integer nombreDePlaceDisponibles;
    @NotNull
    private String immatriculation;
    @NotNull
    private String photoURL;
    @NotNull
    private Double emissionCO2;
    @NotNull
    private Motorisation motorisation;
    @NotNull
    private Statut statut;
    @NotNull
    private Categorie categorie;

    private String marque;

    public VehiculeServiceDto(VehiculeService vehiculeService){
        this.id = vehiculeService.getId();
        this.modele = vehiculeService.getModele().getNom();
        this.nombreDePlaceDisponibles = vehiculeService.getNombreDePlaceDisponibles();
        this.immatriculation= vehiculeService.getImmatriculation();
        this.photoURL= vehiculeService.getPhotoURL();
        this.emissionCO2 = vehiculeService.getEmissionCO2();
        this.motorisation = vehiculeService.getMotorisation();
        this.statut= vehiculeService.getStatut();
        this.categorie = vehiculeService.getCategorie();
        this.marque = vehiculeService.getModele().getMarque().getNom();
    }

}
