package fr.diginamic.gestit_back.utils;

import fr.diginamic.gestit_back.configuration.JWTConfig;
import fr.diginamic.gestit_back.entites.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@AllArgsConstructor
public class JWTUtils {

    public static final String JWT_NAME_IN_HTTP_REQUEST = "JWT-TOKEN";

    private JWTConfig jwtConfig;
    

    public String buildJWT(Utilisateur utilisateur) {
        List<String> roles = new ArrayList<>(utilisateur.getRoles());
        Map map = new HashMap<>();
        map.put("username", utilisateur.getNom());
        map.put("password", utilisateur.getMotDePasse());
        map.put("email", utilisateur.getEmail());
        //map.put("id",utilisateur.getId());
        map.put("roles", roles);
        System.out.println(roles);
        String jetonJWT = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS265")
                .setSubject(String.valueOf(utilisateur.getId()))
                .addClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpireIn() * 1000))
                .signWith(jwtConfig.getSecretKey())
                .compact();
        return jetonJWT;
    }

    public Claims parseJWT(String token) {

        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(jwtConfig.getSecretKey()).build();
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        return claimsJws.getBody();
    }

    /**
     * Extrait l'id d'un utilisateur à partir d'un token inclus dans les en-tête 
     * d'une requête HTTP entrante.
     * 
     * Rappel :  Un JWT représente un ensemble de revendications, appelée Claims, sous la
     * forme d'un objet JSON codé dans une structure de Signature WebJSon(JWS) et/ou
     * de cryptage WebJson (JWE).
     * Il est composé de une ou plusieurs paires clé (String)/valeur(objectJson)
     * associés à chaque Claims.
     * @param httpHeaders les entêtes de la requête HTTP contenant notamment le JWT
     *                    pour l'authentification.
     * @return l'id du propriétaire du token
     * @throws Exception
     * @author AtsuhikoMochizuki
     * 
     */
    
     public Integer extractIdFromHttpHeaders(HttpHeaders httpHeaders){
        /*Récupération du token*/
        List<String> jwtToken = httpHeaders.get(JWTUtils.JWT_NAME_IN_HTTP_REQUEST);

        /* On récupère la première ligne du token qui contient l'id utilisateur */
        String jwtFirstClaim = jwtToken.get(0);

        /* Puis on la traite pour en extraire la revendication */
        Claims revendication = this.parseJWT(jwtFirstClaim);

        /*
         * On récupère la valeur de la paire clé/valeur représentant la revendication,
         * à savoir ici le numéro d'identification du propriétaire du token
         */
        String idOwnerStr = revendication.getSubject();

        /* Conversion de la chaine de caractères en nombre */
        Integer connectedUserId = Integer.decode(idOwnerStr);
        
        return connectedUserId;
     }
     

    }


