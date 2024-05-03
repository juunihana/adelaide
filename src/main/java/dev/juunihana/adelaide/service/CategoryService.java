package dev.juunihana.adelaide.service;

import dev.juunihana.adelaide.dto.category.Category;
import dev.juunihana.adelaide.dto.category.CreateCategory;
import dev.juunihana.adelaide.dto.category.UpdateCategory;
import dev.juunihana.adelaide.dto.item.ItemFull;
import java.util.List;
import java.util.Set;

public interface CategoryService {

  List<Category> findAll();

  Category findById(String id);

  Set<ItemFull> findItemsFromCategory(String categoryId, Integer pageNumber, Integer pageSize);

  Category create(CreateCategory dto);

  Category update(String id, UpdateCategory dto);

  void delete(String id);
}
