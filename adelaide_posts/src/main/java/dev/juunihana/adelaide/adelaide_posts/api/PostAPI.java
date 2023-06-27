package dev.juunihana.adelaide.adelaide_posts.api;

import dev.juunihana.adelaide.adelaide_posts.dto.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_posts.dto.PostDTO;
import java.util.List;
import java.util.Map;

public interface PostAPI {

  PostDTO findById(String id);

  /**
   * Search posts
   * query params passed as GET parameters
   * on frontend these parameters are controlled by vue-router
   * it means when we are routed to some page there are GET params
   * and they are passed to search query from post list component
   */
  List<PostDTO> search(Map<String, String> requestParams);

  //201
  //All posts are either have null in their replyTo field or are replies to other posts
  void create(CreatePostDTO dto);

  //200
  void update(String id, CreatePostDTO dto);

  //204
  void delete(String id);
}
