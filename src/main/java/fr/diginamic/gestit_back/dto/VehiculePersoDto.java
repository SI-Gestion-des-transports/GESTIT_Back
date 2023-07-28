package fr.diginamic.gestit_back.dto;

import fr.diginamic.gestit_back.entites.VehiculePerso;
import fr.diginamic.gestit_back.repository.VehiculePersoRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculePersoDto {

    @NotBlank
    private String modele;
    @NotNull
    private Integer nombreDePlaceDisponibles;
    @NotBlank
    private String immatriculation;
    @NotNull
    private Integer userId;
}
