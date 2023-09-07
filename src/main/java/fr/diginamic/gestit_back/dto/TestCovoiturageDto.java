package fr.diginamic.gestit_back.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TestCovoiturageDto(
         @NotNull Integer nombrePlacesRestantes,
         @NotNull Integer dureeTrajet,
         @NotNull Integer distanceKm,
         @Valid LocalDate dateDepart,
         @NotNull Integer adresseDepartId,
         @NotNull Integer adresseArriveeID,
         @NotNull Integer organisateurId,
         @NotNull Integer vehiculePersoId,
         @NotNull Integer[] passagersId

) {
}
