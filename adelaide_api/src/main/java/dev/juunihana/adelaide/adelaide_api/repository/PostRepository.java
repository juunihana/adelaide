package dev.juunihana.adelaide.adelaide_api.repository;

import dev.juunihana.adelaide.adelaide_api.entity.PostEntity;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {

  List<PostEntity> findAllByUserUsername(String username);

  List<PostEntity> findAllByUserUsernameAndAuthorUsername(String username, String author);
}
