package fr.diginamic.gestit_back.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CovoiturageDtoRecord(
        Integer id,
         @NotNull Integer nombrePlacesRestantes,
         @NotNull Integer dureeTrajet,
         @NotNull Integer distanceKm,
         @Valid LocalDate dateDepart,
         @NotNull AdresseDto adresseDepart,
         @NotNull AdresseDto adresseArrivee,
         @NotNull Integer organisateurId,
         @NotNull Integer vehiculePersoId,
         @NotNull Integer[] passagersId

) {
}
