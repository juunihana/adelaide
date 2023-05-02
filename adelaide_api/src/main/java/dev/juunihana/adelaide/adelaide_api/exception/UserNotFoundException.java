package dev.juunihana.adelaide.adelaide_api.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String errorField) {
    super("User with " + errorField + " was not found");
  }
}
