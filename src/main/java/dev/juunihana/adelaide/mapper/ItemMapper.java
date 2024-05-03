package dev.juunihana.adelaide.mapper;

import dev.juunihana.adelaide.dto.CreateItem;
import dev.juunihana.adelaide.dto.ItemFull;
import dev.juunihana.adelaide.entity.ItemEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {CategoryMapper.class})
public interface ItemMapper {

  ItemFull itemEntityToItemFull(ItemEntity itemEntity);

  ItemEntity itemFullToItemEntity(ItemFull itemFull);

  ItemEntity itemCreateToItemEntity(CreateItem itemFull);
}
