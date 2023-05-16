package dev.juunihana.adelaide.adelaide_api.exception;

public class PasswordPreviouslyUsedException extends RuntimeException {

  public PasswordPreviouslyUsedException() {
    super("You have already used this password");
  }
}
