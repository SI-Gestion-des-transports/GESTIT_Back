package fr.diginamic.gestit_back.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CovoiturageDtoRecord(
        Integer id,
        Integer nombrePlacesRestantes,
        @NotNull Integer dureeTrajet,
        @NotNull Integer distanceKm,
        @Valid LocalDate dateDepart,
        @NotNull Integer adresseDepartId,
        @NotNull Integer adresseArriveeId,
        @NotNull Integer organisateurId,
        @NotNull Integer vehiculePersoId,
        Integer[] passagersId

) {
}
