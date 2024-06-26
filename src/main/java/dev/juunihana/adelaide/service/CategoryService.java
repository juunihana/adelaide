package dev.juunihana.adelaide.service;

import dev.juunihana.adelaide.dto.category.CategoryFull;
import dev.juunihana.adelaide.dto.category.CreateCategory;
import dev.juunihana.adelaide.dto.category.UpdateCategory;
import dev.juunihana.adelaide.dto.product.ProductFull;
import java.util.List;
import java.util.Set;

public interface CategoryService {

  List<CategoryFull> findAll();

  CategoryFull findById(String id);

  Set<ProductFull> findProductsFromCategory(String categoryId, Integer pageNumber, Integer pageSize);

  CategoryFull create(CreateCategory dto);

  CategoryFull update(String id, UpdateCategory dto);

  void delete(String id);
}
