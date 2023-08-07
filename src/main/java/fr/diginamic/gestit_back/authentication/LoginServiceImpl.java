package fr.diginamic.gestit_back.authentication;

import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.utils.JWTUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {
    private AuthenticationManager authenticationManager;
    private JWTUtils jwtUtils;

    @Override
    public ResponseEntity login(Utilisateur utilisateur) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(utilisateur.getNom(), utilisateur.getMotDePasse());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authentication))
            return ResponseEntity.status(401).body("Username or password error !");
        else {
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            utilisateur = loginUser.getUtilisateur();
            String JWTtoken = jwtUtils.buildJWT(utilisateur);
            return ResponseEntity.status(200).body(JWTtoken);
        }

    }
}
