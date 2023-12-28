package com.example.Authorization;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgNotValidException(MethodArgumentNotValidException e) {
        Map<String,String> exceptionResponse=new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((objectError) -> {
            String fieldName= ((FieldError)objectError).getField();
            String message= objectError.getDefaultMessage();
            exceptionResponse.put(fieldName,message);

        });
        return new ResponseEntity<Map<String, String>>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}


