package dev.juunihana.adelaide.adelaide_api.repository;

import dev.juunihana.adelaide.adelaide_api.entity.CommentEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {

}
