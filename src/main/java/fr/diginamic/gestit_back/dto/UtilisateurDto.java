package fr.diginamic.gestit_back.dto;

public record UtilisateurDto(
        String nom,
        String motDePasse,
        String email,
        String role
) {
}
