package dev.juunihana.adelaide.mapper;

import dev.juunihana.adelaide.dto.product.CreateProduct;
import dev.juunihana.adelaide.dto.product.ProductFull;
import dev.juunihana.adelaide.dto.product.UpdateProduct;
import dev.juunihana.adelaide.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(uses = {CategoryMapper.class})
public interface ProductMapper {

  ProductFull productEntityToProductFull(ProductEntity productEntity);

  ProductEntity productFullToProductEntity(ProductFull productFull);

  ProductEntity productCreateToProductEntity(CreateProduct productFull);

  void update(@MappingTarget ProductEntity entity, UpdateProduct dto);
}
