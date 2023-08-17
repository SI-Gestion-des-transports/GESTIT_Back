package fr.diginamic.gestit_back.dto;

import jakarta.validation.constraints.NotBlank;

public record MarqueDto(
        Integer id,
        @NotBlank(message = "Le nom ne doit pas être vide !") String nom
) {
}
