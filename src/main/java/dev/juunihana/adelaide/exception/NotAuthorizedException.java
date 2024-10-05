package dev.juunihana.adelaide.exception;

public class NotAuthorizedException extends RuntimeException {

  public NotAuthorizedException(String message) {
    super(message);
  }
}
