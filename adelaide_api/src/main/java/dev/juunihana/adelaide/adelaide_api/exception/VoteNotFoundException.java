package dev.juunihana.adelaide.adelaide_api.exception;

public class VoteNotFoundException extends RuntimeException {

  public VoteNotFoundException() {
    super("Vote not found");
  }
}
