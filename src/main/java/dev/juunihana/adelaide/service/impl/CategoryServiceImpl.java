package dev.juunihana.adelaide.service.impl;

import dev.juunihana.adelaide.dto.Category;
import dev.juunihana.adelaide.dto.ItemFull;
import dev.juunihana.adelaide.entity.CategoryEntity;
import dev.juunihana.adelaide.exception.CategoryNotFoundException;
import dev.juunihana.adelaide.mapper.CategoryMapper;
import dev.juunihana.adelaide.mapper.ItemMapper;
import dev.juunihana.adelaide.repository.CategoryRepository;
import dev.juunihana.adelaide.repository.ItemRepository;
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
  private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);

  private final CategoryRepository categoryRepository;
  private final ItemRepository itemRepository;

  @Override
  public List<Category> findAll() {
    return categoryRepository.findAll().stream()
        .filter(categoryEntity -> categoryEntity.getParent() == null)
        .map(categoryMapper::categoryEntityToCategory)
        .collect(Collectors.toList());
  }

  @Override
  public Category findById(String id) {
    return categoryMapper.categoryEntityToCategory(categoryRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new CategoryNotFoundException(id)));
  }

  @Override
  public Set<ItemFull> findItemsFromCategory(String categoryId, Integer pageNumber) {
    CategoryEntity parent = categoryRepository.findById(UUID.fromString(categoryId))
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));

    Set<UUID> ids = new HashSet<>();
    ids.add(UUID.fromString(categoryId));
    traverseCategoriesIds(ids, parent);
    return itemRepository.findByCategoryIdIn(ids, PageRequest.of(pageNumber - 1, 10)).stream()
        .map(itemMapper::itemEntityToItemFull)
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
  public Category create(Category dto) {
    CategoryEntity entity = categoryMapper.categoryToCategoryEntity(dto);
    if (StringUtils.hasLength(dto.getParentId())) {
      categoryRepository.findById(UUID.fromString(dto.getParentId()))
          .ifPresent(entity::setParent);
    }

    return categoryMapper.categoryEntityToCategory(categoryRepository.save(entity));
  }

  @Override
  public Category update(String id, Category dto) {
    return null;
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
