package fr.diginamic.gestit_back.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


public record ReservationVehiculeServiceDto (
        Integer id,
        Integer userId,
        @NotNull Integer vehiculeServiceId,
        @Valid LocalDateTime dateHeureDepart,
        @Valid LocalDateTime dateHeureRetour
        ){
}
