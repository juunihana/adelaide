package dev.juunihana.adelaide.adelaide_api.service.impl;

import dev.juunihana.adelaide.adelaide_api.dto.request.comment.CreateCommentDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.post.CreatePostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.request.vote.CreateVoteDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.entity.CommentEntity;
import dev.juunihana.adelaide.adelaide_api.entity.PostEntity;
import dev.juunihana.adelaide.adelaide_api.entity.UserEntity;
import dev.juunihana.adelaide.adelaide_api.entity.VoteEntity;
import dev.juunihana.adelaide.adelaide_api.exception.AccessDeniedException;
import dev.juunihana.adelaide.adelaide_api.exception.PostNotFoundException;
import dev.juunihana.adelaide.adelaide_api.mapper.PostMapper;
import dev.juunihana.adelaide.adelaide_api.mapper.VoteMapper;
import dev.juunihana.adelaide.adelaide_api.repository.CommentRepository;
import dev.juunihana.adelaide.adelaide_api.repository.PostRepository;
import dev.juunihana.adelaide.adelaide_api.repository.VoteRepository;
import dev.juunihana.adelaide.adelaide_api.service.PostService;
import dev.juunihana.adelaide.adelaide_api.service.UserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final PostMapper postMapper;
  private final UserService userService;
  private final VoteRepository voteRepository;
  private final CommentRepository commentRepository;
  private final VoteMapper voteMapper;

  @Override
  public PostDTO findById(String postId) {
    PostEntity post = postRepository.findById(UUID.fromString(postId))
        .orElseThrow(() -> new PostNotFoundException(postId));

    if (post.getDeleted()) {
      throw new PostNotFoundException(postId);
    }

    return postMapper.postEntityToDTO(post);
  }

  @Override
  public List<PostDTO> findAllByUsername(String username, boolean authored) {
    List<PostEntity> posts = (authored ?
        postRepository.findAllByUserUsernameAndAuthorUsername(username, username) :
        postRepository.findAllByUserUsername(username))
        .stream()
        .filter(post -> !post.getDeleted())
        .toList();

    List<PostDTO> dtos = new ArrayList<>();

    posts.forEach(post -> {
      PostDTO postDTO = postMapper.postEntityToDTO(post);

      if (userService.isUserSigned()) {
        postDTO.setVote(voteMapper.voteToDto(
            voteRepository.findByUserAndPost(userService.getSignedUserEntity(), post)
                .orElse(null)));
      }

      dtos.add(postDTO);

    });

    return dtos;
  }

  @Override
  public void create(CreatePostDTO createPostDTO) {
    UUID postId = UUID.randomUUID();

    UserEntity user = userService.getSignedUserEntity();
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
  }

  @Override
  public void update(String postId, CreatePostDTO createPostDTO) {
    PostEntity post = findByIdInternal(postId);

    if (post.getDeleted()) {
      throw new PostNotFoundException(postId);
    }

    String authorUsername = userService.getSignedUser().getUsername();
    if (!post.getUser().getUsername().equals(authorUsername)) {
      throw new AccessDeniedException("You cannot edit this user posts");
    }

    post.setTitle(createPostDTO.getTitle());
    post.setContent(createPostDTO.getContent());
    post.setTimeEdited(LocalDateTime.now());

    postRepository.save(post);
  }

  @Override
  public void delete(String postId) {
    PostEntity post = findByIdInternal(postId);

    if (post.getDeleted()) {
      throw new PostNotFoundException(postId);
    }

    String authorUsername = userService.getSignedUserEntity().getUsername();
    if (!post.getUser().getUsername().equals(authorUsername)) {
      throw new AccessDeniedException("You cannot delete this user posts");
    }

    post.setTimeEdited(LocalDateTime.now());
    post.setDeleted(true);

    postRepository.save(post);
  }

  public void addVote(CreateVoteDTO dto) {
    PostEntity post = findByIdInternal(dto.getTargetId());

    if (post.getDeleted()) {
      throw new PostNotFoundException(dto.getTargetId());
    }

    UserEntity user = userService.getSignedUserEntity();

    VoteEntity vote = voteRepository.findByUserAndPost(user, post)
        .orElse(null);

    if (Objects.isNull(vote)) {
      voteRepository.save(VoteEntity.builder()
          .upVote(dto.isUpVote())
          .timeVoted(LocalDateTime.now())
          .user(user)
          .post(post)
          .build());
    } else {
      vote.setUpVote(dto.isUpVote());
      vote.setTimeVoted(LocalDateTime.now());
      voteRepository.save(vote);
    }
  }

  @Override
  public void addComment(CreateCommentDTO dto) {
    PostEntity post = findByIdInternal(dto.getTargetId());

    if (post.getDeleted()) {
      throw new PostNotFoundException(dto.getTargetId());
    }

    UserEntity user = userService.getSignedUserEntity();

    commentRepository.save(CommentEntity.builder()
        .author(user)
        .post(post)
        .content(dto.getContent())
        .timeCreated(LocalDateTime.now())
        .build());
  }

  private PostEntity findByIdInternal(String postId) {
    return postRepository.findById(UUID.fromString(postId))
        .orElseThrow(() -> new PostNotFoundException(postId));
  }
}
