package dev.juunihana.adelaide.adelaide_api.repository;

import dev.juunihana.adelaide.adelaide_api.entity.UserEntity;
import dev.juunihana.adelaide.adelaide_api.entity.VoteEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<VoteEntity, UUID> {

  boolean existsByUser(UserEntity user);

  Optional<VoteEntity> findByUser(UserEntity user);
}
