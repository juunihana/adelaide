package dev.juunihana.adelaide.service.impl;

import dev.juunihana.adelaide.dto.item.CreateItem;
import dev.juunihana.adelaide.dto.item.ItemFull;
import dev.juunihana.adelaide.dto.item.UpdateItem;
import dev.juunihana.adelaide.entity.ItemEntity;
import dev.juunihana.adelaide.exception.CategoryNotFoundException;
import dev.juunihana.adelaide.exception.ItemNotFoundException;
import dev.juunihana.adelaide.mapper.ItemMapper;
import dev.juunihana.adelaide.repository.CategoryRepository;
import dev.juunihana.adelaide.repository.ItemRepository;
import dev.juunihana.adelaide.service.ItemService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
  public ItemFull findById(String id) {
    return itemMapper.itemEntityToItemFull(itemRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new ItemNotFoundException(id)));
  }

  @Override
  @Transactional
  public ItemFull create(CreateItem dto) {
    ItemEntity entity = itemMapper.itemCreateToItemEntity(dto);

    entity.setCategory(categoryRepository.findById(UUID.fromString(dto.getCategoryId()))
        .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryId())));

    return itemMapper.itemEntityToItemFull(itemRepository.save(entity));
  }

  @Override
  @Transactional
  public ItemFull update(String id, UpdateItem dto) {
    ItemEntity entity = itemRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new ItemNotFoundException(id));

    itemMapper.update(entity, dto);

    if (StringUtils.hasLength(dto.getCategoryId())) {
      entity.setCategory(categoryRepository.findById(UUID.fromString(dto.getCategoryId()))
          .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryId())));
    }

    return itemMapper.itemEntityToItemFull(itemRepository.save(entity));
  }

  @Override
  @Transactional
  public void delete(String id) {
    itemRepository.deleteById(UUID.fromString(id));
  }
}
