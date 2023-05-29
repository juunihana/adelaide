package dev.juunihana.adelaide.adelaide_api.exception;

public class VoteNotFoundException extends RuntimeException {

  public VoteNotFoundException() {
    super("Vote not found");
  }

  public VoteNotFoundException(String voteId) {
    super("Vote with id " + voteId + " not found");
  }
}
