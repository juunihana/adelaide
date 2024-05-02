package dev.juunihana.adelaide.service.impl;

import dev.juunihana.adelaide.dto.ItemFull;
import dev.juunihana.adelaide.entity.CategoryEntity;
import dev.juunihana.adelaide.entity.ItemEntity;
import dev.juunihana.adelaide.mapper.ItemMapper;
import dev.juunihana.adelaide.repository.CategoryRepository;
import dev.juunihana.adelaide.repository.ItemRepository;
import dev.juunihana.adelaide.service.ItemService;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

  private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);

  private final ItemRepository itemRepository;
  private final CategoryRepository categoryRepository;

  @Override
  public List<ItemFull> findAll() {
    return itemRepository.findAll(PageRequest.of(1, 10)).stream()
        .map(itemMapper::itemEntityToItemFull)
        .toList();
  }

  @Override
  public ItemFull findById(UUID id) {
    return null;
  }

  @Override
  @Transactional
  public Set<ItemFull> findAllByCategory(UUID categoryId, Integer page) {
    CategoryEntity parent = categoryRepository.findById(categoryId).orElse(null);

    Set<UUID> ids = new HashSet<>();
    ids.add(categoryId);
    traverseCategoriesIds(ids, parent);

    return itemRepository.findByCategoryIdIn(ids, PageRequest.of(page - 1, 10)).stream()
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
  public ItemFull create(ItemFull dto) {
    ItemEntity entity = itemMapper.itemFullToItemEntity(dto);

    CategoryEntity categoryEntity =
        categoryRepository.findById(UUID.fromString(dto.getCategory().getId())).orElse(null);
    entity.setCategory(categoryEntity);

    return itemMapper.itemEntityToItemFull(itemRepository.save(entity));
  }

  @Override
  public ItemFull update(UUID id, ItemFull dto) {
    return null;
  }

  @Override
  public void delete(UUID id) {
    itemRepository.deleteById(id);
  }
}
