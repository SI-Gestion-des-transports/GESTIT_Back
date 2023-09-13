package fr.diginamic.gestit_back.dto;

import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.entites.VehiculeService;
import fr.diginamic.gestit_back.service.UtilisateurService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record UtilisateurDto(
        Integer id,
        @NotNull String nom,
        @NotNull String motDePasse,
        @NotNull @Email String email,
        List<String> roles
) {

}
