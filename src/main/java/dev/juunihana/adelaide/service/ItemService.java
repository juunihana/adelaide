package dev.juunihana.adelaide.service;

import dev.juunihana.adelaide.dto.CreateItem;
import dev.juunihana.adelaide.dto.ItemFull;
import java.util.List;
import java.util.UUID;

public interface ItemService {

  List<ItemFull> findAll();

  ItemFull findById(String id);

  ItemFull create(CreateItem dto);

  ItemFull update(String id, ItemFull dto);

  void delete(String id);
}
