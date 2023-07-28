package fr.diginamic.gestit_back.dto;

import fr.diginamic.gestit_back.entites.Utilisateur;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ReservationVehiculeServiceDto (
        @NotBlank Integer userId,
        @NotBlank Integer vehiculeServiceId,
        @NotBlank LocalDateTime dateHeureDepart,
        @NotBlank LocalDateTime dateHeureRetour
        ){
}
