package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.SuccessPostDTO;
import dev.juunihana.adelaide.adelaide_api.entity.PostEntity;
import dev.juunihana.adelaide.adelaide_api.entity.UserEntity;
import dev.juunihana.adelaide.adelaide_api.entity.VoteEntity;
import dev.juunihana.adelaide.adelaide_api.exception.AccessDeniedException;
import dev.juunihana.adelaide.adelaide_api.exception.PostNotFoundException;
import dev.juunihana.adelaide.adelaide_api.mapper.PostMapper;
import dev.juunihana.adelaide.adelaide_api.repository.PostRepository;
import dev.juunihana.adelaide.adelaide_api.repository.VoteRepository;
import dev.juunihana.adelaide.adelaide_api.service.PostService;
import dev.juunihana.adelaide.adelaide_api.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final PostMapper postMapper;
  private final UserService userService;
  private final VoteRepository voteRepository;

  @Override
  public PostDTO findById(String postId) {
    PostEntity post = postRepository.findById(UUID.fromString(postId))
        .orElseThrow(() -> new PostNotFoundException(postId));

    if (post.getDeleted()) {
      throw new AccessDeniedException();
    }

    return postMapper.postEntityToDTO(post);
  }

  @Override
  public List<PostDTO> findAllByUsername(String username, boolean authored) {
    List<PostEntity> posts = authored ?
        postRepository.findAllByUserUsernameAndAuthorUsername(username, username).stream()
            .filter(post -> !post.getDeleted())
            .toList() :
        postRepository.findAllByUserUsername(username).stream()
            .filter(post -> !post.getDeleted())
            .toList();

    return posts.stream()
        .map(postMapper::postEntityToDTO)
        .collect(Collectors.toList());
  }

  @Override
  public SuccessPostDTO create(CreatePostDTO createPostDTO) {
    UUID postId = UUID.randomUUID();

    UserEntity user = (UserEntity) userService.loadUserByUsername(createPostDTO.getUsername());
    UserEntity author = (UserEntity) userService.loadUserByUsername(
        userService.getSignedUser().getUsername());

    postRepository.save(PostEntity.builder()
        .id(postId)
        .user(user)
        .author(author)
        .title(createPostDTO.getTitle())
        .content(createPostDTO.getContent())
        .timeCreated(LocalDateTime.now())
        .deleted(false)
        .build());

    return SuccessPostDTO.builder()
        .postId(postId.toString())
        .build();
  }

  public void addVote(String postId, boolean upVote) {
    PostEntity post = postRepository.findById(UUID.fromString(postId))
        .orElseThrow(() -> new PostNotFoundException(postId));

    UserEntity user = (UserEntity) userService.loadUserByUsername(
        userService.getSignedUser().getUsername());

    if (!voteRepository.existsByUser(user)) {
      voteRepository.save(VoteEntity.builder()
          .upvote(upVote)
          .timeVoted(LocalDateTime.now())
          .user(user)
          .post(post)
          .build());
    }
  }

  public void removeVote(String postId) {
    PostEntity post = postRepository.findById(UUID.fromString(postId))
        .orElseThrow(() -> new PostNotFoundException(postId));

    UserEntity user = (UserEntity) userService.loadUserByUsername(
        userService.getSignedUser().getUsername());

    VoteEntity vote = voteRepository.findByUser(user)
        .orElseThrow(() -> new RuntimeException());

    voteRepository.delete(vote);
  }

  @Override
  public SuccessPostDTO edit(String postId, CreatePostDTO createPostDTO) {
    PostEntity post = postRepository.findById(UUID.fromString(postId))
        .orElseThrow(() -> new PostNotFoundException(postId));

    String authorUsername = userService.getSignedUser().getUsername();
    if (!post.getUser().getUsername().equals(authorUsername)) {
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
    PostEntity post = postRepository.findById(UUID.fromString(postId))
        .orElseThrow(() -> new PostNotFoundException(postId));

    String authorUsername = userService.getSignedUser().getUsername();
    if (!post.getUser().getUsername().equals(authorUsername)) {
      throw new AccessDeniedException("You cannot delete this user posts");
    }

    post.setTimeEdited(LocalDateTime.now());
    post.setDeleted(true);

    postRepository.save(post);
  }
}
