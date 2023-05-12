package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.SuccessPostDTO;
import dev.juunihana.adelaide.adelaide_api.entity.PostEntity;
import dev.juunihana.adelaide.adelaide_api.entity.UserEntity;
import dev.juunihana.adelaide.adelaide_api.exception.AccessDeniedException;
import dev.juunihana.adelaide.adelaide_api.exception.PostNotFoundException;
import dev.juunihana.adelaide.adelaide_api.exception.UserNotFoundException;
import dev.juunihana.adelaide.adelaide_api.mapper.PostMapper;
import dev.juunihana.adelaide.adelaide_api.repository.PostRepository;
import dev.juunihana.adelaide.adelaide_api.service.PostService;
import dev.juunihana.adelaide.adelaide_api.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final PostMapper postMapper;
  private final UserService userService;

  @Override
  public PostDTO findById(String postId) {
    return postMapper.postEntityToDTO(postRepository.findById(UUID.fromString(postId))
        .orElseThrow(() -> new PostNotFoundException(postId)));
  }

  @Override
  public List<PostDTO> findAllByUsername(String username) {
    return postRepository.findAllByUserUserAuthUsername(username).stream()
        .map(postMapper::postEntityToDTO)
        .collect(Collectors.toList());
  }

  @Override
  public SuccessPostDTO create(String username, CreatePostDTO createPostDTO) {
    UUID postId = UUID.randomUUID();

    UserEntity user = userService.findByUsername(username);
    UserEntity author = userService.findByUsername(
        ((UserDetails) SecurityContextHolder.getContext().getAuthentication()).getUsername());

    postRepository.save(PostEntity.builder()
        .id(postId)
        .user(user)
        .author(author)
        .title(createPostDTO.getTitle())
        .content(createPostDTO.getContent())
        .timeCreated(LocalDateTime.now())
        .build());

    return SuccessPostDTO.builder()
        .postId(postId.toString())
        .build();
  }

  @Override
  public SuccessPostDTO createOnUserProfile(String username, CreatePostDTO createPostDTO) {
    return null;
  }

  @Override
  public SuccessPostDTO createOnUserProfile(CreatePostDTO createPostDTO) {
    return null;
  }

  @Override
  public SuccessPostDTO edit(String postId, CreatePostDTO createPostDTO) {
    PostEntity post = postRepository.findById(UUID.fromString(postId))
        .orElseThrow(() -> new PostNotFoundException(postId));

    String authorUsername = getCurrentUserUsername();
    if (!post.getUser().getUserAuth().getUsername().equals(authorUsername)) {
      throw new AccessDeniedException("You cannot edit this user posts");
    }

    post.setTitle(createPostDTO.getTitle());
    post.setContent(createPostDTO.getContent());
    post.setTimeEdited(LocalDateTime.now());

    postRepository.save(post);

    return SuccessPostDTO.builder()
        .postId(postId)
        .build();
  }

  @Override
  public void delete(String postId) {

  }
}
