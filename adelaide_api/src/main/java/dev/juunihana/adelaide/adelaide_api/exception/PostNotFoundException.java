package dev.juunihana.adelaide.adelaide_api.exception;

public class PostNotFoundException extends RuntimeException {

  public PostNotFoundException(String postId) {
    super(postId);
  }
}
