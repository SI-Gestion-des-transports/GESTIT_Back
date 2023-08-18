package fr.diginamic.gestit_back.utils;

import fr.diginamic.gestit_back.configuration.JWTConfig;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.enumerations.Role;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@AllArgsConstructor
public class JWTUtils {
    private JWTConfig jwtConfig;

    public String buildJWT(Utilisateur utilisateur){
        List<String> roles = new ArrayList<>(utilisateur.getRoles());
        Map map= new HashMap<>();
        map.put("username",utilisateur.getNom());
        map.put("password",utilisateur.getMotDePasse());
        //map.put("email",utilisateur.getEmail());
        //map.put("id",utilisateur.getId());
        map.put("roles",roles);
        String jetonJWT = Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS265")
                .setSubject(String.valueOf(utilisateur.getId()))
                .addClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpireIn() * 1000))
                .signWith(jwtConfig.getSecretKey())
                .compact();
        return jetonJWT;
    }

    public Claims parseJWT(String token){

                JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(jwtConfig.getSecretKey()).build();
                Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
                return claimsJws.getBody();
    }
}
