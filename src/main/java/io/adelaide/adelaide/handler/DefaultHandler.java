package io.adelaide.adelaide.handler;

import io.adelaide.adelaide.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> handle404(NotFoundException exception) {
    return ResponseEntity.notFound().build();
  }
}
