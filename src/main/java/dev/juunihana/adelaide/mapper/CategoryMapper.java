package dev.juunihana.adelaide.mapper;

import dev.juunihana.adelaide.dto.category.Category;
import dev.juunihana.adelaide.dto.category.CategoryCompact;
import dev.juunihana.adelaide.dto.category.CreateCategory;
import dev.juunihana.adelaide.dto.category.UpdateCategory;
import dev.juunihana.adelaide.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(uses = CategoryMapper.class)
public interface CategoryMapper {

  Category categoryEntityToCategory(CategoryEntity category);

  CategoryCompact categoryEntityToCategoryCompact(CategoryEntity category);

  CategoryEntity categoryToCategoryEntity(Category category);

  CategoryEntity createCategoryToCategoryEntity(CreateCategory category);

  void update(@MappingTarget CategoryEntity entity, UpdateCategory dto);
}
