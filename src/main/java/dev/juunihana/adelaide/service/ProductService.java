package dev.juunihana.adelaide.service;

import dev.juunihana.adelaide.dto.product.CreateProductDto;
import dev.juunihana.adelaide.dto.product.ProductFullDto;
import dev.juunihana.adelaide.dto.product.UpdateProductDto;
import dev.juunihana.adelaide.entity.ProductEntity;
import java.util.List;

public interface ProductService {

  List<ProductFullDto> findAll();

  ProductFullDto findById(String id);

  ProductEntity findEntityById(String id);

  ProductFullDto create(CreateProductDto dto);

  ProductFullDto update(String id, UpdateProductDto dto);

  void delete(String id);
}
