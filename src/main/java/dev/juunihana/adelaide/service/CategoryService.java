package dev.juunihana.adelaide.service;

import dev.juunihana.adelaide.dto.Category;
import java.util.List;
import java.util.UUID;

public interface CategoryService {

  List<Category> findAll();

  Category findById(String id);

  Category create(Category dto);

  Category update(String id, Category dto);

  void delete(String id);
}
