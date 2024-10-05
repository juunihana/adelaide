package dev.juunihana.adelaide.service;

import dev.juunihana.adelaide.dto.product.CreateProductDto;
import dev.juunihana.adelaide.dto.product.ProductFull;
import dev.juunihana.adelaide.dto.product.UpdateProductDto;
import java.util.List;

public interface ProductService {

  List<ProductFull> findAll();

  ProductFull findById(String id);

  ProductFull create(CreateProductDto dto);

  ProductFull update(String id, UpdateProductDto dto);

  void delete(String id);
}
