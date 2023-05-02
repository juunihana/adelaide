package dev.juunihana.adelaide.adelaide_api.handler;

import dev.juunihana.adelaide.adelaide_api.constant.ErrorMessage;
import dev.juunihana.adelaide.adelaide_api.dto.response.error.ErrorDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.error.ValidationErrorDTO;
import dev.juunihana.adelaide.adelaide_api.exception.UserAlreadyExistsException;
import dev.juunihana.adelaide.adelaide_api.exception.UserNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class DataExceptionHandler extends ResponseEntityExceptionHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(UserNotFoundException.class)
  protected ErrorDTO handleUserNotFound(UserNotFoundException e) {
    System.out.println("ERROR 400: " + e.getMessage());
    return ErrorDTO.builder()
        .result("userNotFoundError")
        .message(e.getMessage())
        .build();
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e,
      HttpHeaders headers, HttpStatusCode statusCode, WebRequest webRequest) {
    System.out.println("ERROR 404: " + e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ErrorDTO.builder()
            .result("notFound")
            .message(e.getMessage())
            .build());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(UserAlreadyExistsException.class)
  protected ErrorDTO handleUserAlreadyExists(UserAlreadyExistsException e) {
    System.out.println("ERROR 400: " + e.getMessage());
    return ErrorDTO.builder()
        .result("userAlreadyExistsError")
        .message(e.getMessage())
        .build();
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(DataIntegrityViolationException.class)
  protected ErrorDTO handleDataIntegrityData(DataIntegrityViolationException e) {
    System.out.println("ERROR 500: " + e.getMessage());
    return ErrorDTO.builder()
        .result("internalServerError")
        .message(ErrorMessage.SERVER_ERROR)
        .build();
  }

  /**
   * Collect all field errors into map (keys are fields that failed validation and values are lists
   * of errors), and then retrieve only the first message from each list to response
   * On the front end we don't need to show every message (e.g. if user has not specified username,
   * there will be four errors at once) and we can only show one
   */
  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
      HttpHeaders headers, HttpStatusCode statusCode, WebRequest webRequest) {
    Map<String, List<String>> fieldsWithErrors = e.getFieldErrors().stream()
        .sorted(Comparator.comparing(FieldError::getField))
        .collect(Collectors.groupingBy(FieldError::getField,
            Collectors.mapping(FieldError::getDefaultMessage,
                Collectors.collectingAndThen(Collectors.toList(),
                    message -> message.stream()
                        .sorted()
                        .collect(Collectors.toList())))));

    System.out.println("ERROR 400: " + fieldsWithErrors);

    return ResponseEntity.badRequest().body(
        ValidationErrorDTO.builder()
            .result("validationError")
            .errorFields(fieldsWithErrors.values().stream()
                .map(messageList -> messageList.get(0))
                .collect(Collectors.toList()))
            .build());
  }
}
