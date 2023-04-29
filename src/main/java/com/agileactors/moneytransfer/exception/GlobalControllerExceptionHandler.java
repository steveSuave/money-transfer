package com.agileactors.moneytransfer.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> handleAPIExceptions(Exception ex) {
    return new ResponseEntity<>(
        new ErrorMessage(ex.getMessage()), new HttpHeaders(), HttpStatus.valueOf(409));
  }
}
