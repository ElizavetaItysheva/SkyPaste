package com.example.myawesomepastebin.controller.controllerAdvice;

import com.example.myawesomepastebin.exception.ExpiredPastaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExpiredPastaHandler {
    @ExceptionHandler(ExpiredPastaException.class)
    public ResponseEntity<?> handleExpiredPastaException(){
        return ResponseEntity.status(404).body("Паста просрочена.");
    }
}
