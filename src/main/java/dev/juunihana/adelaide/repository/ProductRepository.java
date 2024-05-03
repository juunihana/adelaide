package dev.juunihana.adelaide.repository;

import dev.juunihana.adelaide.entity.ProductEntity;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, UUID> {

  Optional<ProductEntity> findById(UUID id);

  List<ProductEntity> findByCategoryIdIn(Set<UUID> ids, Pageable pageRequest);

  ProductEntity save(ProductEntity entity);

  void deleteById(UUID id);
}
