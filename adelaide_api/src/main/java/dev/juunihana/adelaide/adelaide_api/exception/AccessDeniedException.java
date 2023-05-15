package dev.juunihana.adelaide.adelaide_api.exception;

public class AccessDeniedException extends RuntimeException {

  public AccessDeniedException() {
    super();
  }

  public AccessDeniedException(String message) {
    super(message);
  }
}
