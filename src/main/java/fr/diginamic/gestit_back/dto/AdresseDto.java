package fr.diginamic.gestit_back.dto;

import jakarta.validation.constraints.NotNull;

public record AdresseDto(
        String commune,
        Integer codePostal,
        Integer numero,
        String voie
) {
}
