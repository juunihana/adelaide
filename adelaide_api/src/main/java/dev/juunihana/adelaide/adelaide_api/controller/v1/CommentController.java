package dev.juunihana.adelaide.adelaide_api.controller.v1;

import dev.juunihana.adelaide.adelaide_api.api.v1.CommentApi;
import dev.juunihana.adelaide.adelaide_api.dto.request.comment.CreateCommentDTO;
import dev.juunihana.adelaide.adelaide_api.service.CommentService;
import dev.juunihana.adelaide.adelaide_api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController implements CommentApi {

  private final PostService postService;
  private final CommentService commentService;

  @Override
  public void create(CreateCommentDTO dto) {
    switch (dto.getTargetType()) {
      case "post" -> postService.addComment(dto);
    }
  }

  @Override
  public void update(String commentId, CreateCommentDTO dto) {
    commentService.update(commentId, dto);
  }

  @Override
  public void delete(String commentId) {
    commentService.delete(commentId);
  }
}
