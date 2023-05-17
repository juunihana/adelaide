package dev.juunihana.adelaide.adelaide_cdn.repository;

import dev.juunihana.adelaide.adelaide_cdn.entity.ImageEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {

  Optional<ImageEntity> findByFileName(String fileName);
}
