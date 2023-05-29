package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.dto.request.comment.CreateCommentDTO;
import dev.juunihana.adelaide.adelaide_api.entity.CommentEntity;
import dev.juunihana.adelaide.adelaide_api.entity.VoteEntity;
import dev.juunihana.adelaide.adelaide_api.exception.AccessDeniedException;
import dev.juunihana.adelaide.adelaide_api.exception.CommentNotFoundException;
import dev.juunihana.adelaide.adelaide_api.exception.VoteNotFoundException;
import dev.juunihana.adelaide.adelaide_api.repository.CommentRepository;
import dev.juunihana.adelaide.adelaide_api.service.CommentService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;

  @Override
  public void update(String commentId, CreateCommentDTO dto) {
    CommentEntity comment = findByIdInternal(commentId);

    if (!comment.getAuthor().getUsername().equals(
        ((UserDetails) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal()).getUsername())) {
      throw new AccessDeniedException("You can't edit this user's votes");
    }

    comment.setContent(dto.getContent());

    commentRepository.save(comment);
  }

  @Override
  public void delete(String commentId) {
    CommentEntity comment = findByIdInternal(commentId);
    commentRepository.delete(comment);
  }

  @Override
  public void addVote(String commentId, boolean upVote) {

  }

  private CommentEntity findByIdInternal(String commentId) {
    return commentRepository.findById(UUID.fromString(commentId))
        .orElseThrow(() -> new CommentNotFoundException(commentId));
  }
}
