package fr.diginamic.gestit_back.dto;

import fr.diginamic.gestit_back.entites.VehiculePerso;
import fr.diginamic.gestit_back.repository.VehiculePersoRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculePersoDto {
    private String marque;
    private String modele;
    private String nombreDePlaceDisponibles;
    private String immatriculation;
    private Integer userId;
}
