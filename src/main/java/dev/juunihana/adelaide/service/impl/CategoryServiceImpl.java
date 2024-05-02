package dev.juunihana.adelaide.service.impl;

import dev.juunihana.adelaide.dto.Category;
import dev.juunihana.adelaide.entity.CategoryEntity;
import dev.juunihana.adelaide.mapper.CategoryMapper;
import dev.juunihana.adelaide.repository.CategoryRepository;
import dev.juunihana.adelaide.service.CategoryService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

  private final CategoryRepository categoryRepository;

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
        .orElse(null));
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
    categoryRepository.deleteById(UUID.fromString(id));
  }
}
