package dev.juunihana.adelaide.mapper;

import dev.juunihana.adelaide.dto.category.CategoryCompactDto;
import dev.juunihana.adelaide.dto.category.CategoryFullDto;
import dev.juunihana.adelaide.dto.category.CreateCategoryDto;
import dev.juunihana.adelaide.dto.category.UpdateCategoryDto;
import dev.juunihana.adelaide.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(uses = CategoryMapper.class)
public interface CategoryMapper {

  CategoryFullDto categoryEntityToCategoryFull(CategoryEntity category);

  CategoryCompactDto categoryEntityToCategoryCompact(CategoryEntity category);

  CategoryEntity categoryFullToCategoryEntity(CategoryFullDto categoryFullDto);

  CategoryEntity createCategoryToCategoryEntity(CreateCategoryDto category);

  void update(@MappingTarget CategoryEntity entity, UpdateCategoryDto dto);
}
