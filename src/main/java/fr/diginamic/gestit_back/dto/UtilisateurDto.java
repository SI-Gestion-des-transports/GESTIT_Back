package fr.diginamic.gestit_back.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class UtilisateurDto {
        private String nom;
        private String motDePasse;
        private String email;
        private String role;
}
