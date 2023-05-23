package dev.juunihana.adelaide.adelaide_api.controller.v1;

import dev.juunihana.adelaide.adelaide_api.api.v1.PostApi;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.AddVoteDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.SuccessPostDTO;
import dev.juunihana.adelaide.adelaide_api.service.PostService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController implements PostApi {

  private final PostService postService;

  @Override
  @GetMapping("/{postId}")
  public PostDTO findById(
      @PathVariable String postId) {
    return postService.findById(postId);
  }

  @Override
  @GetMapping("/profile/{username}")
  public List<PostDTO> findAllByUsername(
      @PathVariable String username,
      @RequestParam(required = false) boolean authored) {
    return postService.findAllByUsername(username, authored);
  }

  @Override
  @PostMapping("/new")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  @ResponseStatus(HttpStatus.CREATED)
  public SuccessPostDTO create(
      @RequestBody @Valid CreatePostDTO createPostDTO) {
    return postService.create(createPostDTO);
  }

  @Override
  @PutMapping("/{postId}")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  public SuccessPostDTO edit(
      @PathVariable String postId,
      @RequestBody @Valid CreatePostDTO createPostDTO) {
    return postService.edit(postId, createPostDTO);
  }

  @Override
  @DeleteMapping("/{postId}")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @PathVariable String postId) {
    postService.delete(postId);
  }

  @PostMapping("/{postId}/vote")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  public void addVote(
      @PathVariable String postId,
      @RequestParam Boolean upVote
  ) {
    postService.addVote(postId, upVote);
  }

  @DeleteMapping("/{postId}/vote")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  public void removeVote(
      @PathVariable String postId
  ) {
    postService.removeVote(postId);
  }

}
