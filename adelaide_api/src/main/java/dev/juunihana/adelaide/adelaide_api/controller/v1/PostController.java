package dev.juunihana.adelaide.adelaide_api.controller.v1;

import dev.juunihana.adelaide.adelaide_api.api.v1.PostApi;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController implements PostApi {

  private final PostService postService;

  @Override
  public PostDTO findById(String postId) {
    return postService.findById(postId);
  }

  @GetMapping("/{postId}/vote")
  public PostDTO findByIdVotes(@PathVariable String postId) {
    return postService.findById(postId);
  }

  @Override
  public List<PostDTO> findAllByUsername(String username, boolean authored) {
    return postService.findAllByUsername(username, authored);
  }

  @Override
  public void create(CreatePostDTO createPostDTO) {
    postService.create(createPostDTO);
  }

  @Override
  public void update(String postId, CreatePostDTO createPostDTO) {
    postService.update(postId, createPostDTO);
  }

  @Override
  public void delete(String postId) {
    postService.delete(postId);
  }

}
