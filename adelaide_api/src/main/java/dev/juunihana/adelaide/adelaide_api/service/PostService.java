package dev.juunihana.adelaide.adelaide_api.service;

import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.SuccessPostDTO;
import java.util.List;

public interface PostService {

  SuccessPostDTO create(CreatePostDTO createPostDTO);

  PostDTO findById(String id);

  List<PostDTO> findAllByUser(String username);
}
