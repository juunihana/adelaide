package dev.juunihana.adelaide.service;

import dev.juunihana.adelaide.dto.ItemFull;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ItemService {

  List<ItemFull> findAll();

  ItemFull findById(UUID id);

  Set<ItemFull> findAllByCategory(UUID categoryId, Integer page);

  ItemFull create(ItemFull dto);

  ItemFull update(UUID id, ItemFull dto);

  void delete(UUID id);
}
