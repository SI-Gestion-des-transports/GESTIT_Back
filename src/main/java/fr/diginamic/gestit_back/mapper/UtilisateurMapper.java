package fr.diginamic.gestit_back.mapper;

import org.mapstruct.Mapper;

import fr.diginamic.gestit_back.dto.UtilisateurDto;
import fr.diginamic.gestit_back.entites.Utilisateur;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    Utilisateur DtotoUtilisateur(UtilisateurDto utilisateurDto);
}
