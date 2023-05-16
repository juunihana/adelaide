package dev.juunihana.adelaide.adelaide_api.repository;

import dev.juunihana.adelaide.adelaide_api.entity.PasswordHistoryEntity;
import dev.juunihana.adelaide.adelaide_api.entity.UserEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistoryEntity, UUID> {

  List<PasswordHistoryEntity> findAllByUser(UserEntity user);
}
