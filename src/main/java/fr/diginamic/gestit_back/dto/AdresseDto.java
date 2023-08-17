package fr.diginamic.gestit_back.dto;

public record AdresseDto(
                String commune,
                Integer codePostal,
                Integer numero,
                String voie) {
}
