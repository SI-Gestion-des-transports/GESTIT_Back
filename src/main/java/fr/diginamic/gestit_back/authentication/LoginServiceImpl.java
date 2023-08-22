package fr.diginamic.gestit_back.authentication;

import fr.diginamic.gestit_back.configuration.JWTConfig;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.utils.JWTUtils;
import fr.diginamic.gestit_back.utils.RedisUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {
    private AuthenticationManager authenticationManager;
    private JWTUtils jwtUtils;
    private RedisUtils redisUtils;
    private JWTConfig jwtConfig;




    @Override
    public ResponseEntity login(Utilisateur utilisateur) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(utilisateur.getEmail(), utilisateur.getMotDePasse());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authentication))
            return ResponseEntity.status(403).body("Username or password error !");
        else {
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            utilisateur = loginUser.getUtilisateur();
            String JWTtoken = jwtUtils.buildJWT(utilisateur);
            redisUtils.createRedisCache(utilisateur.getEmail(), JWTtoken);
            Map<String,String> map = new HashMap<>();
            map.put(jwtConfig.getName(),JWTtoken);
            map.put("userId", String.valueOf(loginUser.getUtilisateur().getId()));
            return ResponseEntity.status(200).body(map);
        }

    }



    @Override
    public ResponseEntity signout(HttpHeaders httpHeaders) {
        String token = httpHeaders.get(jwtConfig.getName()).get(0);
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(loginUser.getUsername());
        System.out.println(token);
        redisUtils.deleteRedisCache(loginUser.getUsername(),token);
        return ResponseEntity.status(200).body("Logout successful ! Token deleted!");
    }

    @Override
    public ResponseEntity getId(HttpHeaders httpHeaders) {
        String token = httpHeaders.get(jwtConfig.getName()).get(0);
        return ResponseEntity.status(200).body(jwtUtils.parseJWT(token).getSubject());
    }
}
