package fr.diginamic.gestit_back.authentication;

import fr.diginamic.gestit_back.entites.Utilisateur;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity getId(HttpHeaders httpHeaders);

    ResponseEntity login(Utilisateur utilisateur);

    ResponseEntity signout(HttpHeaders httpHeaders);
}
