package dev.juunihana.adelaide.adelaide_api.exception;

public class PasswordsDoesNotMatchException extends RuntimeException {

  public PasswordsDoesNotMatchException() {
    super("Passwords does not match");
  }
}
