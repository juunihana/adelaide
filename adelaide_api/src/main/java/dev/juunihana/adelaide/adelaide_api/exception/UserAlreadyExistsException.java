package dev.juunihana.adelaide.adelaide_api.exception;

public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(String errorField) {
    super("User with " + errorField + " already exists");
  }
}
