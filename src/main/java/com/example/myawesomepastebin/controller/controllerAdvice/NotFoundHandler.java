package com.example.myawesomepastebin.controller.controllerAdvice;

import com.example.myawesomepastebin.exception.PastaNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class NotFoundHandler {
    @ExceptionHandler(PastaNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(){
        return ResponseEntity.status(404).body("Паста не найдена.");
    }
}
