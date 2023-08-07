package fr.diginamic.gestit_back.authentication;

import fr.diginamic.gestit_back.entites.Utilisateur;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity login(Utilisateur utilisateur);
}
