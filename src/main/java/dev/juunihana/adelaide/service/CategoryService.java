package dev.juunihana.adelaide.service;

import dev.juunihana.adelaide.dto.category.Category;
import dev.juunihana.adelaide.dto.item.ItemFull;
import java.util.List;
import java.util.Set;

public interface CategoryService {

  List<Category> findAll();

  Category findById(String id);

  Set<ItemFull> findItemsFromCategory(String categoryId, Integer pageNumber);

  Category create(Category dto);

  Category update(String id, Category dto);

  void delete(String id);
}
