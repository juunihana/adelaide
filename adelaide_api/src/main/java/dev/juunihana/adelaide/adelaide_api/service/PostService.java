package dev.juunihana.adelaide.adelaide_api.service;

import dev.juunihana.adelaide.adelaide_api.dto.request.post.AddVoteDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.SuccessPostDTO;
import java.util.List;

public interface PostService {

  PostDTO findById(String id);

  List<PostDTO> findAllByUsername(String username, boolean authored);

  SuccessPostDTO create(CreatePostDTO createPostDTO);

  SuccessPostDTO edit(String postId, CreatePostDTO createPostDTO);

  void delete(String postId);

  void addVote(String postId, boolean upVote);

  void removeVote(String postId);
}
