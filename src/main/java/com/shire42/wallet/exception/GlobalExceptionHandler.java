package com.shire42.wallet.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidateErrors(WalletNotFoundException ex) {
        return new ResponseEntity<>(Map.of("errors", List.of(ex.getMessage())) , new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
