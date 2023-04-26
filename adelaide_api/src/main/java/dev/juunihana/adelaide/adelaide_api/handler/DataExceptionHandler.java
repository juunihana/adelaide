package dev.juunihana.adelaide.adelaide_api.handler;

import dev.juunihana.adelaide.adelaide_api.dto.error.UserNotFoundDTO;
import dev.juunihana.adelaide.adelaide_api.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class DataExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {
      UserNotFoundException.class
  })
  protected ResponseEntity<?> handleUserNotFound(RuntimeException e, WebRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(UserNotFoundDTO.builder()
            .username(e.getMessage())
            .build());
  }
}
