package fr.diginamic.gestit_back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GestionException {

    @ExceptionHandler({Exception.class})
    public void gestionExceptions(Exception e){
        System.out.println("Erreur :" + e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> forException(MethodArgumentNotValidException notValidArgException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notValidArgException.getDetailMessageArguments());
    }
}
