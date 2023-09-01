package fr.diginamic.gestit_back.dto;

import fr.diginamic.gestit_back.entites.VehiculePerso;
import fr.diginamic.gestit_back.enumerations.Statut;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculePersoDto {

    private Integer id;
    @NotBlank
    private String modele;
    @NotNull
    private Integer nombreDePlaceDisponibles;
    @NotBlank
    private String immatriculation;

    private Integer userId;

    public VehiculePersoDto(VehiculePerso vehiculePerso){
        this.id= vehiculePerso.getId();
        this.modele= vehiculePerso.getModele().getNom();
        this.userId= vehiculePerso.getProprietaire().getId();
        this.nombreDePlaceDisponibles = vehiculePerso.getNombreDePlaceDisponibles();
        this.immatriculation = vehiculePerso.getImmatriculation();
    }
}
