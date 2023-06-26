package dev.juunihana.adelaide.adelaide_api.api.v1;

import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.PostDTO;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
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
  PostDTO findById(@PathVariable String postId);

  @GetMapping
  List<PostDTO> findAll();

  @GetMapping("/profile/{username}")
  List<PostDTO> findAllByUsername(@PathVariable String username,
      @RequestParam Map<String, String> requestParams);

  @PostMapping("/new")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  @ResponseStatus(HttpStatus.CREATED)
  void create(@RequestBody @Valid CreatePostDTO dto);

  @PutMapping("/{postId}")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  void update(@PathVariable String postId, @RequestBody @Valid CreatePostDTO dto);

  @DeleteMapping("/{postId}")
  @PreAuthorize("hasRole('ROLE_USER_NOT_ANON')")
  void delete(@PathVariable String postId);
}
