package org.jzeratul.mykid.rest;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleResourceException(Exception exception, WebRequest request) {
    return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  public static ResponseEntity<Object> handleExceptionInternal(
    Exception exception,
    Object body,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    return new ResponseEntity<>(body, headers, status);
  }
}