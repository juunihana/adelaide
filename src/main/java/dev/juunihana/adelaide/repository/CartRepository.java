package dev.juunihana.adelaide.repository;

import dev.juunihana.adelaide.entity.CartEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, UUID> {

  Optional<CartEntity> findByUserId(UUID userId);
}
