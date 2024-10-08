package dev.juunihana.adelaide.handler;

import dev.juunihana.adelaide.dto.error.ErrorDto;
import dev.juunihana.adelaide.exception.BadRequestException;
import dev.juunihana.adelaide.exception.NotAuthorizedException;
import dev.juunihana.adelaide.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class HttpExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(HttpExceptionHandler.class);

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorDto handle(NotFoundException e) {
    logger.error(e.getMessage());

    return ErrorDto.builder()
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto handleBadRequestException(BadRequestException e) {
    logger.error(e.getMessage());

    return ErrorDto.builder()
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(NotAuthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorDto handleNotAuthorizedException(NotAuthorizedException e) {
    logger.error(e.getMessage());

    return ErrorDto.builder()
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public void handleIllegalArgumentException(IllegalArgumentException e) {
    logger.error(e.getMessage());
  }
}
