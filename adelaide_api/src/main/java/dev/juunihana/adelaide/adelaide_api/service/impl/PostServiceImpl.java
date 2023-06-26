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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
  public List<PostDTO> findAll() {
    return postRepository.findAll().stream()
        .filter(post -> !post.getDeleted())
        .map(postMapper::postEntityToDTO)
        .collect(Collectors.toList());
  }

  @Override
  public PostDTO findByIdVotes(String id) {
    PostEntity post = findByIdInternalNotDeleted(id);

    return PostDTO.builder()
        .vote(voteMapper.voteToDto(post.getVotes().stream()
            .filter(vote -> vote.getUser().getUsername()
                .equals(userService.getSignedUser().getUsername()))
            .findFirst().orElse(null)))
        .upVotes((int) post.getVotes().stream().filter(VoteEntity::isUpVote).count())
        .downVotes((int) post.getVotes().stream().filter(vote -> !vote.isUpVote()).count())
        .build();
  }

  @Override
  public List<PostDTO> findAllByUsername(String username, Map<String, String> requestParams) {
    boolean authored = requestParams.containsKey("authored") && Boolean.parseBoolean(
        requestParams.get("authored"));
    boolean order = requestParams.containsKey("order") && Boolean.parseBoolean(
        requestParams.get("order"));

    List<PostDTO> posts = postRepository.findAllByUserUsername(username).stream()
        .filter(post ->
            (!authored || post.getAuthor().getUsername().equals(username)) && !post.getDeleted())
        .map(postMapper::postEntityToDTO)
        .sorted(
            switch (requestParams.get("sortBy")) {
              case "time" -> Comparator.comparing(PostDTO::getTimeCreated);
              case "upVotes" -> Comparator.comparing(PostDTO::getUpVotes);
              default -> Comparator.comparing(PostDTO::getTimeCreated);
            })
        .collect(Collectors.toList());

    posts.forEach(post -> post.setVote(userService.isUserSigned() ? voteMapper.voteToDto(
        voteRepository.findByUserAndPostId(userService.getSignedUserEntity(), post.getId())
            .orElse(null)) : null));

    if (!order) {
      Collections.reverse(posts);
    }

    return posts;
  }

  @Override
  public List<PostDTO> search(Map<String, String> searchParams) {
    String username = Optional.ofNullable(searchParams.get("username")).orElse("");
    String tags = Optional.ofNullable(searchParams.get("tags")).orElse("");
    String query = Optional.ofNullable(searchParams.get("query")).orElse("");
    String sortBy = Optional.ofNullable(searchParams.get("sortBy")).orElse("time");
    String order = Optional.ofNullable(searchParams.get("order")).orElse("desc");

    List<PostEntity> poststmp = StringUtils.hasLength(username) ?
        postRepository.findAllByUserUsername(username) :
        postRepository.findAll();

    List<PostEntity> posts = postRepository.findAll().stream()
        .filter(post -> !StringUtils.hasLength(username) || post.getUser().getUsername()
            .equals(username))
        .filter(post -> !StringUtils.hasLength(tags) || post.getTags().stream()
            .anyMatch(tag -> List.of(tags.split(",")).contains(tag.getName())))
        .filter(post -> !StringUtils.hasLength(query)
            || post.getTitle().toLowerCase().contains(query.toLowerCase())
            || post.getContent().toLowerCase().contains(query.toLowerCase()))
        .sorted(switch (sortBy) {
//          case "time" -> Comparator.comparing(PostEntity::getTimeCreated);
          case "rating" -> Comparator.comparing(post -> post.getVotes().stream().filter(VoteEntity::isUpVote).count());
          default -> Comparator.comparing(PostEntity::getTimeCreated);
        })
        .collect(Collectors.toList());

    if ("desc".equals(order)) {
      Collections.reverse(posts);
    }

    return null;
  }

  @Override
  public void create(CreatePostDTO createPostDTO) {
    postRepository.save(PostEntity.builder()
        .user(userService.getByUsername(createPostDTO.getUsername()))
        .author(userService.getSignedUserEntity())
        .title(createPostDTO.getTitle())
        .content(createPostDTO.getContent())
        .timeCreated(LocalDateTime.now())
        .deleted(false)
        .build());
  }

  @Override
  public void update(String postId, CreatePostDTO createPostDTO) {
    PostEntity post = findByIdInternalNotDeleted(postId);

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
    PostEntity post = findByIdInternalNotDeleted(postId);

    String authorUsername = userService.getSignedUserEntity().getUsername();
    if (!post.getUser().getUsername().equals(authorUsername)) {
      throw new AccessDeniedException("You cannot delete this user posts");
    }

    post.setTimeEdited(LocalDateTime.now());
    post.setDeleted(true);

    postRepository.save(post);
  }

  public void addVote(CreateVoteDTO dto) {
    PostEntity post = findByIdInternalNotDeleted(dto.getTargetId());
    UserEntity user = userService.getSignedUserEntity();

    voteRepository.findByUserAndPostId(user, post.getId())
        .ifPresentOrElse(vote -> {
          vote.setUpVote(dto.isUpVote());
          vote.setTimeVoted(LocalDateTime.now());
          voteRepository.save(vote);
        }, () -> voteRepository.save(VoteEntity.builder()
            .upVote(dto.isUpVote())
            .timeVoted(LocalDateTime.now())
            .user(user)
            .post(post)
            .build()));
  }

  @Override
  public void addComment(CreateCommentDTO dto) {
    commentRepository.save(CommentEntity.builder()
        .author(userService.getSignedUserEntity())
        .post(findByIdInternalNotDeleted(dto.getTargetId()))
        .content(dto.getContent())
        .timeCreated(LocalDateTime.now())
        .build());
  }

  private PostEntity findByIdInternal(String postId) {
    return postRepository.findById(UUID.fromString(postId))
        .orElseThrow(() -> new PostNotFoundException(postId));
  }

  private PostEntity findByIdInternalNotDeleted(String postId) {
    return postRepository.findById(UUID.fromString(postId))
        .filter(post -> !post.getDeleted())
        .orElseThrow(() -> new PostNotFoundException(postId));
  }
}
