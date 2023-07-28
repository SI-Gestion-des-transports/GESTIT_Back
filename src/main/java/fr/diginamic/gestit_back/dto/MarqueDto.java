package fr.diginamic.gestit_back.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MarqueDto(
        @NotBlank(message = "Le nom ne doit pas être vide !") String nom
) {
}
