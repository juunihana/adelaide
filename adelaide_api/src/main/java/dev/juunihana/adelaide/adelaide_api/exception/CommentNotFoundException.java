package dev.juunihana.adelaide.adelaide_api.exception;

public class CommentNotFoundException extends RuntimeException {

  public CommentNotFoundException(String commentId) {
    super("Comment with id" + commentId + " not found");
  }
}
