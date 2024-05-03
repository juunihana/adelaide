package dev.juunihana.adelaide.service;

import dev.juunihana.adelaide.dto.item.CreateItem;
import dev.juunihana.adelaide.dto.item.ItemFull;
import dev.juunihana.adelaide.dto.item.UpdateItem;
import java.util.List;

public interface ItemService {

  List<ItemFull> findAll();

  ItemFull findById(String id);

  ItemFull create(CreateItem dto);

  ItemFull update(String id, UpdateItem dto);

  void delete(String id);
}
