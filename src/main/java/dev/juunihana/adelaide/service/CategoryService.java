package dev.juunihana.adelaide.service;

import dev.juunihana.adelaide.dto.category.CategoryFullDto;
import dev.juunihana.adelaide.dto.category.CreateCategoryDto;
import dev.juunihana.adelaide.dto.category.UpdateCategoryDto;
import dev.juunihana.adelaide.dto.product.ProductFullDto;
import java.util.List;
import java.util.Set;

public interface CategoryService {

  List<CategoryFullDto> findAll();

  CategoryFullDto findById(String id);

  Set<ProductFullDto> findProductsFromCategory(String categoryId, Integer pageNumber, Integer pageSize);

  CategoryFullDto create(CreateCategoryDto dto);

  CategoryFullDto update(String id, UpdateCategoryDto dto);

  void delete(String id);
}
