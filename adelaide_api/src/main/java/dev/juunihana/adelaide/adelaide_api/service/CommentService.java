package dev.juunihana.adelaide.adelaide_api.service;

import dev.juunihana.adelaide.adelaide_api.dto.request.comment.CreateCommentDTO;

public interface CommentService {

  void update(String commentId, CreateCommentDTO dto);

  void delete(String commentId);

  void addVote(String commentId, boolean upVote);
}
