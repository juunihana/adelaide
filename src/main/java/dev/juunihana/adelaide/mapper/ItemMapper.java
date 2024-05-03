package dev.juunihana.adelaide.mapper;

import dev.juunihana.adelaide.dto.item.CreateItem;
import dev.juunihana.adelaide.dto.item.ItemFull;
import dev.juunihana.adelaide.dto.item.UpdateItem;
import dev.juunihana.adelaide.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(uses = {CategoryMapper.class})
public interface ItemMapper {

  ItemFull itemEntityToItemFull(ItemEntity itemEntity);

  ItemEntity itemFullToItemEntity(ItemFull itemFull);

  ItemEntity itemCreateToItemEntity(CreateItem itemFull);

  void update(@MappingTarget ItemEntity entity, UpdateItem dto);
}
