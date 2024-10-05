package dev.juunihana.adelaide.mapper;

import dev.juunihana.adelaide.dto.cart.CartDto;
import dev.juunihana.adelaide.entity.CartEntity;
import org.mapstruct.Mapper;

@Mapper
public interface CartMapper {

  CartDto cartEntityToDto(CartEntity entity);
}
