package dev.juunihana.adelaide.service.impl;

import dev.juunihana.adelaide.dto.category.CategoryFull;
import dev.juunihana.adelaide.dto.category.CreateCategory;
import dev.juunihana.adelaide.dto.category.UpdateCategory;
import dev.juunihana.adelaide.dto.product.ProductFull;
import dev.juunihana.adelaide.entity.CategoryEntity;
import dev.juunihana.adelaide.exception.CategoryNotFoundException;
import dev.juunihana.adelaide.mapper.CategoryMapper;
import dev.juunihana.adelaide.mapper.ProductMapper;
import dev.juunihana.adelaide.repository.CategoryRepository;
import dev.juunihana.adelaide.repository.ProductRepository;
import dev.juunihana.adelaide.service.CategoryService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
  private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;

  @Override
  public List<CategoryFull> findAll() {
    return categoryRepository.findAll().stream()
        .filter(categoryEntity -> categoryEntity.getParent() == null)
        .map(categoryMapper::categoryEntityToCategoryFull)
        .collect(Collectors.toList());
  }

  @Override
  public CategoryFull findById(String id) {
    return categoryMapper.categoryEntityToCategoryFull(categoryRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new CategoryNotFoundException(id)));
  }

  @Override
  public Set<ProductFull> findProductsFromCategory(String categoryId, Integer pageNumber,
      Integer pageSize) {
    CategoryEntity parent = categoryRepository.findById(UUID.fromString(categoryId))
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));

    Set<UUID> ids = new HashSet<>();
    ids.add(UUID.fromString(categoryId));
    traverseCategoriesIds(ids, parent);
    return productRepository.findByCategoryIdIn(ids,
            PageRequest.of(pageNumber - 1, pageSize == null ? 10 : pageSize)).stream()
        .map(productMapper::productEntityToProductFull)
        .collect(Collectors.toSet());
  }

  private void traverseCategoriesIds(Set<UUID> ids, CategoryEntity category) {
    if (!category.getSubCategories().isEmpty()) {
      ids.addAll(category.getSubCategories().stream()
          .map(CategoryEntity::getId)
          .collect(Collectors.toSet()));
      for (CategoryEntity subCategory : category.getSubCategories()) {
        traverseCategoriesIds(ids, subCategory);
      }
    }
  }


  @Override
  public CategoryFull create(CreateCategory dto) {
    CategoryEntity entity = categoryMapper.createCategoryToCategoryEntity(dto);
    if (StringUtils.hasLength(dto.getParentId())) {
      categoryRepository.findById(UUID.fromString(dto.getParentId()))
          .ifPresent(entity::setParent);
    }

    return categoryMapper.categoryEntityToCategoryFull(categoryRepository.save(entity));
  }

  @Override
  public CategoryFull update(String id, UpdateCategory dto) {
    CategoryEntity entity = categoryRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new CategoryNotFoundException(id));

    categoryMapper.update(entity, dto);

    if (StringUtils.hasLength(dto.getParentId())) {
      entity.setParent(categoryRepository.findById(UUID.fromString(dto.getParentId()))
          .orElseThrow(() -> new CategoryNotFoundException(id)));
    }

    return categoryMapper.categoryEntityToCategoryFull(categoryRepository.save(entity));
  }

  @Override
  public void delete(String id) {
    if (categoryRepository.existsById(UUID.fromString(id))) {
      categoryRepository.deleteById(UUID.fromString(id));
    } else {
      throw new CategoryNotFoundException(id);
    }
  }
}
