package fr.diginamic.gestit_back.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UtilisateurDtoClass {
    @NotNull
    private String nom;
    @NotNull
    private String motDePasse;
    @NotNull
    @Email
    private String email;
    @NotNull
    private List<String> roles;
    private LocalDate dateNonValide;

}
