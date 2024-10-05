package dev.juunihana.adelaide.exception;

public class UserAlreadyExistsByPhoneException extends RuntimeException {

  public UserAlreadyExistsByPhoneException(String message) {
    super(message);
  }
}
