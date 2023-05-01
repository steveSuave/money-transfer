package com.agileactors.moneytransfer.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> handleAPIExceptions(Exception ex) {
    LOGGER.error("Handling Exception:", ex);
    return new ResponseEntity<>(
        new ErrorMessage(ex.getMessage(), MDC.get("traceId")),
        new HttpHeaders(),
        HttpStatus.valueOf(409));
  }
}
