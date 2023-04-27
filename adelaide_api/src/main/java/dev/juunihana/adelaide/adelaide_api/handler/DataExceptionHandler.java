package dev.juunihana.adelaide.adelaide_api.handler;

import dev.juunihana.adelaide.adelaide_api.constant.DataConstraints;
import dev.juunihana.adelaide.adelaide_api.constant.ErrorMessage;
import dev.juunihana.adelaide.adelaide_api.dto.response.error.ErrorDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.error.UserNotFoundDTO;
import dev.juunihana.adelaide.adelaide_api.exception.UserNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class DataExceptionHandler extends ResponseEntityExceptionHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(UserNotFoundException.class)
  protected UserNotFoundDTO handleUserNotFound(UserNotFoundException e) {
    return UserNotFoundDTO.builder()
        .message(ErrorMessage.USER_NOT_FOUND_USERNAME)
        .username(e.getMessage())
        .build();
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  protected ResponseEntity<ErrorDTO> handleDataIntegrityData(
      DataIntegrityViolationException e) {
    if (e.getMessage().contains(DataConstraints.USER_EMAIL_CONSTRAINT)) {
      return ResponseEntity.badRequest()
          .body(ErrorDTO.builder().message(ErrorMessage.USER_EXISTS_EMAIL).build());
    }

    if (e.getMessage().contains(DataConstraints.USER_USERNAME_CONSTRAINT)) {
      return ResponseEntity.badRequest()
          .body(ErrorDTO.builder().message(ErrorMessage.USER_EXISTS_USERNAME).build());
    }

    if (e.getMessage().contains(DataConstraints.USER_PHONE_CONSTRAINT)) {
      return ResponseEntity.badRequest()
          .body(ErrorDTO.builder().message(ErrorMessage.USER_EXISTS_PHONE).build());
    }

    return ResponseEntity.internalServerError()
        .body(ErrorDTO.builder().message(ErrorMessage.SERVER_ERROR).build());
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
      HttpHeaders headers, HttpStatusCode statusCode, WebRequest webRequest) {
    Map<String, String> fieldErrors = new LinkedHashMap<>();
    e.getBindingResult().getFieldErrors().forEach(fieldError ->
        fieldErrors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage()));

    return ResponseEntity.badRequest().body(fieldErrors);
  }
}
