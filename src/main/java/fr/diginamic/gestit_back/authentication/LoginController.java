package fr.diginamic.gestit_back.authentication;

import fr.diginamic.gestit_back.configuration.JWTConfig;
import fr.diginamic.gestit_back.dto.UtilisateurDto;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.utils.JWTUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping

@AllArgsConstructor
public class LoginController {
    private LoginService loginService;
    private JWTConfig jwtConfig;
    private JWTUtils jwtUtils;

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

    @GetMapping("/getuserbyjwt")
    public ResponseEntity<UtilisateurDto> getUser(@RequestHeader HttpHeaders httpHeaders){
        Integer userId = Integer.decode(jwtUtils.parseJWT(httpHeaders.get(jwtConfig.getName()).get(0)).getSubject());
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur utilisateur = loginUser.getUtilisateur();
        UtilisateurDto dto = new UtilisateurDto(userId, utilisateur.getNom(),"gestitsecret", utilisateur.getEmail(),utilisateur.getRoles());
        return ResponseEntity.status(200).body(dto);
    }
}
