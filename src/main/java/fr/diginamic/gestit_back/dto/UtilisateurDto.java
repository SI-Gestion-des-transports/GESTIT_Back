package fr.diginamic.gestit_back.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record UtilisateurDto(
        @NotNull String nom,
        @NotNull String motDePasse,
        @NotNull @Email String email
) {
}
