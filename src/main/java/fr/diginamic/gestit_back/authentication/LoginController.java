package fr.diginamic.gestit_back.authentication;

import fr.diginamic.gestit_back.entites.Utilisateur;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping

@AllArgsConstructor
public class LoginController {
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Utilisateur utilisateur) {
        return loginService.login(utilisateur);
    }

    @GetMapping("/signout")
    public ResponseEntity signout(@RequestHeader HttpHeaders httpHeaders) {
        return loginService.signout(httpHeaders);
    }

    @PostMapping("/getidbyjwt")
    public ResponseEntity getId(@RequestHeader HttpHeaders httpHeaders) {
        return loginService.getId(httpHeaders);
    }
}
