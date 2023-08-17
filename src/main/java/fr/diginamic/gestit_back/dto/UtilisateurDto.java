package fr.diginamic.gestit_back.dto;

import lombok.Data;

@Data
public class UtilisateurDto {
        private String nom;
        private String motDePasse;
        private String email;
        private String role;
}
