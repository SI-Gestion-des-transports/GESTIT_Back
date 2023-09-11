package fr.diginamic.gestit_back.dto;


import jakarta.persistence.criteria.CriteriaBuilder;

public record AdresseDto(
        Integer id,
        String commune,
        Integer codePostal,
        Integer numero,
        String voie
) {
}
