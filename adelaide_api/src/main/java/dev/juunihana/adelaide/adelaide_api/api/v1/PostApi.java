package dev.juunihana.adelaide.adelaide_api.api.v1;


import dev.juunihana.adelaide.adelaide_api.dto.request.post.PostDTO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface PostApi {

  @GetMapping("/post/{postId}")
  PostDTO findById(
      @PathVariable String postId);

  @GetMapping("/post/user/{username}")
  List<PostDTO> findAllByUser(
      @PathVariable String username);
}
