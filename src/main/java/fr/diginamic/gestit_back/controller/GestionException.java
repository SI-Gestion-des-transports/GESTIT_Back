package fr.diginamic.gestit_back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GestionException {

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity accessDeniedException(AccessDeniedException e){

        System.out.println("Erreur :" + e.getMessage());
        return ResponseEntity.status(401).body("Erreur :" + e.getMessage());
    }
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity authenticationException(AuthenticationException e){
        System.out.println("Erreur :" + e.getMessage());
        return ResponseEntity.status(401).body("Erreur :" + e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> forException(MethodArgumentNotValidException notValidArgException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notValidArgException.getDetailMessageArguments());
    }
}
