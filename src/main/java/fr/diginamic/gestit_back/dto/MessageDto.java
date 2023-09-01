package fr.diginamic.gestit_back.dto;

import jakarta.validation.constraints.NotNull;

public record MessageDto(
        @NotNull String message
) {
}
