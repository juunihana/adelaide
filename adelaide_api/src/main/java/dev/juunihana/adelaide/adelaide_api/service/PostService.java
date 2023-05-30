package dev.juunihana.adelaide.adelaide_api.service;

import dev.juunihana.adelaide.adelaide_api.dto.request.comment.CreateCommentDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.vote.CreateVoteDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.PostDTO;
import java.util.List;

public interface PostService {

  PostDTO findById(String id);

  PostDTO findByIdVotes(String id);

  List<PostDTO> findAllByUsername(String username, boolean authored);

  void create(CreatePostDTO dto);

  void update(String postId, CreatePostDTO dto);

  void delete(String postId);

  void addVote(CreateVoteDTO upVote);

  void addComment(CreateCommentDTO dto);
}
