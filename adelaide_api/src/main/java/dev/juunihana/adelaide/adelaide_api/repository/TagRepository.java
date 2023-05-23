package dev.juunihana.adelaide.adelaide_api.repository;

import dev.juunihana.adelaide.adelaide_api.entity.TagEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, UUID> {

}
