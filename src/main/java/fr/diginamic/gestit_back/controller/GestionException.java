package fr.diginamic.gestit_back.controller;

import fr.diginamic.gestit_back.exceptions.NotFoundOrValidException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GestionException {

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity getExpiredJwtException(ExpiredJwtException e){
        return ResponseEntity.status(403).body("Erreur ExpiredJwtException:"+e.getMessage() );
    }
    @ExceptionHandler({JwtException.class})
    public ResponseEntity getJwtException(JwtException e){
        return ResponseEntity.status(403).body("Erreur JwtException:"+ e.getMessage() );
    }
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity getAccessDeniedException(AccessDeniedException e){

        System.out.println("Erreur :" + e.getMessage());
        return ResponseEntity.status(401).body("Erreur :" + e.getMessage());
    }
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity getAuthenticationException(AuthenticationException e){
        System.out.println("Erreur :" + e.getMessage());
        return ResponseEntity.status(403).body("Erreur :" + "usename or password wrong");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> getMethodArgumentNotValidException(MethodArgumentNotValidException notValidArgException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notValidArgException.getDetailMessageArguments());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> getRuntimeException(RuntimeException e){
        return ResponseEntity.status(403).body("Error : "+e.getMessage());
    }

    @ExceptionHandler(NotFoundOrValidException.class)
    public ResponseEntity getNotFoundOrValidException(NotFoundOrValidException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessageDto());
    }


}
