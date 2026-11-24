package com.example.banking.api;

import com.example.banking.service.BankingException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BankingException.class)
    public ResponseEntity<Map<String,String>> banking(BankingException ex){
        HttpStatus status=ex.getMessage().startsWith("Account not found")?HttpStatus.NOT_FOUND:HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(Map.of("error",ex.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> validation(MethodArgumentNotValidException ex){
        String message=ex.getBindingResult().getFieldErrors().stream().findFirst()
            .map(e->e.getField()+": "+e.getDefaultMessage()).orElse("Invalid request");
        return ResponseEntity.badRequest().body(Map.of("error",message));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> unexpected(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error","Unexpected server error"));
    }
}
