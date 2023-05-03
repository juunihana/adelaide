package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.SuccessPostDTO;
import dev.juunihana.adelaide.adelaide_api.entity.PostEntity;
import dev.juunihana.adelaide.adelaide_api.entity.UserAuthEntity;
import dev.juunihana.adelaide.adelaide_api.exception.UserNotFoundException;
import dev.juunihana.adelaide.adelaide_api.mapper.PostMapper;
import dev.juunihana.adelaide.adelaide_api.repository.PostRepository;
import dev.juunihana.adelaide.adelaide_api.repository.UserAuthRepository;
import dev.juunihana.adelaide.adelaide_api.service.PostService;
import dev.juunihana.adelaide.adelaide_api.service.UserAuthService;
import dev.juunihana.adelaide.adelaide_api.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final UserAuthRepository userAuthRepository;
  private final PostMapper postMapper;

  @Override
  public SuccessPostDTO create(CreatePostDTO createPostDTO) {
    UUID postId = UUID.randomUUID();
    String username = ((UserAuthEntity)SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal()).getUsername();

    postRepository.save(PostEntity.builder()
        .id(postId)
        .user(userAuthRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(username)))
        .title(createPostDTO.getTitle())
        .content(createPostDTO.getContent())
        .timeCreated(LocalDateTime.now())
        .build());

    return SuccessPostDTO.builder()
        .postId(postId.toString())
        .build();
  }

  @Override
  public PostDTO findById(String id) {
    return postMapper.postEntityToDTO(postRepository.findById(UUID.fromString(id)).get());
  }

  @Override
  public List<PostDTO> findAllByUser(String username) {
    return null;
  }
}
