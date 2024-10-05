package dev.juunihana.adelaide.exception;

public class UserAlreadyExistsByEmailException extends RuntimeException {

  public UserAlreadyExistsByEmailException(String message) {
    super(message);
  }
}
