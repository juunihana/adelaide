package dev.juunihana.adelaide.adelaide_api.exception;

public class NotAuthorizedException extends RuntimeException {

  public NotAuthorizedException(String message) {
    super(message);
  }
}
