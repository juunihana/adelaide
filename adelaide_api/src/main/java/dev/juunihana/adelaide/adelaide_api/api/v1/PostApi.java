package dev.juunihana.adelaide.adelaide_api.api.v1;

import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.SuccessPostDTO;
import java.util.List;

public interface PostApi {

  PostDTO findById(String postId);

  List<PostDTO> findAllByUsername(String username);

  SuccessPostDTO create(CreatePostDTO createPostDTO);

  SuccessPostDTO edit(String postId, CreatePostDTO createPostDTO);

  void delete(String postId);
}
