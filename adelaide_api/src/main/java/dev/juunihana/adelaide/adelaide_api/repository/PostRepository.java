package dev.juunihana.adelaide.adelaide_api.repository;

import dev.juunihana.adelaide.adelaide_api.entity.PostEntity;
import dev.juunihana.adelaide.adelaide_api.entity.TagEntity;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {

  List<PostEntity> findAllByOrderByTimeCreatedAsc();
  List<PostEntity> findAllByOrderByTimeCreatedDesc();

  @Query("SELECT p from PostEntity p ORDER BY (SELECT COUNT(v) FROM VoteEntity v WHERE v.upVote = true) DESC")
  List<PostEntity> findAllByOrderByCountVotesDesc();

  List<PostEntity> findDistinctByTagsIn(Set<TagEntity> tags);

  List<PostEntity> findAllByUserUsername(String username);

  List<PostEntity> findAllByUserUsernameAndAuthorUsername(String username, String author);
}
