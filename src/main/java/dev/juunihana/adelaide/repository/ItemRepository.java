package dev.juunihana.adelaide.repository;

import dev.juunihana.adelaide.entity.ItemEntity;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<ItemEntity, UUID> {

  Optional<ItemEntity> findById(UUID id);

  List<ItemEntity> findByCategoryIdIn(Set<UUID> ids, Pageable pageRequest);

  ItemEntity save(ItemEntity entity);

  void deleteById(UUID id);
}
