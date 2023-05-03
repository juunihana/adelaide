package dev.juunihana.adelaide.adelaide_api.service;

import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.SuccessPostDTO;

public interface PostService {

  SuccessPostDTO create(CreatePostDTO createPostDTO);

}
