package dev.juunihana.adelaide.mapper;

import dev.juunihana.adelaide.dto.Category;
import dev.juunihana.adelaide.dto.CategoryCompact;
import dev.juunihana.adelaide.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(uses = CategoryMapper.class)
public interface CategoryMapper {

  Category categoryEntityToCategory(CategoryEntity category);

  CategoryCompact categoryEntityToCategoryCompact(CategoryEntity category);

  CategoryEntity categoryToCategoryEntity(Category category);
}
