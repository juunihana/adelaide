package dev.juunihana.adelaide.adelaide_api.repository;

import dev.juunihana.adelaide.adelaide_api.entity.UserAuthEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuthEntity, UUID> {

  Optional<UserAuthEntity> findByUsername(String username);
  Optional<UserAuthEntity> findByEmail(String email);
}
