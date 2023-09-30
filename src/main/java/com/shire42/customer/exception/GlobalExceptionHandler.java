package com.shire42.customer.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidateErrors(CustomerAlreadyExistsException ex) {
        return new ResponseEntity<>(Map.of("errors:", List.of("Customer with the same email already exists")),
                new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidateErrors(CustomerNotFoundException ex) {
        return new ResponseEntity<>(Map.of("errors", List.of(ex.getMessage())) , new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
