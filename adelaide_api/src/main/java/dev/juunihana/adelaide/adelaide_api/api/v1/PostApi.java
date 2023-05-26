package dev.juunihana.adelaide.adelaide_api.api.v1;

import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.SuccessPostDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Validated
@RequestMapping("/api/v1/posts")
public interface PostApi {

  @GetMapping("/{postId}")
  PostDTO findById(
      @PathVariable String postId);

  @GetMapping("/profile/{username}")
  List<PostDTO> findAllByUsername(
      @PathVariable String username,
      @RequestParam(required = false) boolean authored);

  @PostMapping("/new")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  @ResponseStatus(HttpStatus.CREATED)
  SuccessPostDTO create(
      @RequestBody @Valid CreatePostDTO createPostDTO);

  @PutMapping("/{postId}")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  SuccessPostDTO update(
      @PathVariable String postId,
      @RequestBody @Valid CreatePostDTO createPostDTO);

  @DeleteMapping("/{postId}")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void delete(
      @PathVariable String postId);

  @PostMapping("/{postId}/vote")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  void addVote(
      @PathVariable String postId,
      @RequestParam boolean upVote);

  @DeleteMapping("/{postId}/vote")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  void removeVote(
      @PathVariable String postId);
}
