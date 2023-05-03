package dev.juunihana.adelaide.adelaide_api.controller.v1;

import dev.juunihana.adelaide.adelaide_api.api.v1.PostApi;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController implements PostApi {

  private final PostService postService;

  @Override
  public PostDTO findById(String postId) {
    return postService.findById(postId);
  }

  @Override
  public List<PostDTO> findAllByUser(String username) {
    return postService.findAllByUser(username);
  }
}
