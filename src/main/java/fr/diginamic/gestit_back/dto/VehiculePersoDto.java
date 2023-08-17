package fr.diginamic.gestit_back.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculePersoDto {

    @NotBlank
    private String modele;
    @NotBlank
    private Integer nombreDePlaceDisponibles;
    @NotBlank
    private String immatriculation;
    @NotBlank
    private Integer userId;
}
