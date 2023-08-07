package fr.diginamic.gestit_back.authentication;

import fr.diginamic.gestit_back.entites.Utilisateur;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
@AllArgsConstructor
public class LoginController {
    private LoginService loginService;
    @PostMapping
    public ResponseEntity login(@RequestBody Utilisateur utilisateur){
        return loginService.login(utilisateur);
    }
}
