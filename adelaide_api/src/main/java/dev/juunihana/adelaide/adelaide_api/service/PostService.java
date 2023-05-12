package dev.juunihana.adelaide.adelaide_api.service;

import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.SuccessPostDTO;
import java.util.List;

public interface PostService {

  PostDTO findById(String id);

  List<PostDTO> findAllByUsername(String username);

  SuccessPostDTO create(String username, CreatePostDTO createPostDTO);

  SuccessPostDTO edit(String postId, CreatePostDTO createPostDTO);

  void delete(String postId);
}
