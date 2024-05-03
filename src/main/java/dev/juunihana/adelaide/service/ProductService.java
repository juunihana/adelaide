package dev.juunihana.adelaide.service;

import dev.juunihana.adelaide.dto.product.CreateProduct;
import dev.juunihana.adelaide.dto.product.ProductFull;
import dev.juunihana.adelaide.dto.product.UpdateProduct;
import java.util.List;

public interface ProductService {

  List<ProductFull> findAll();

  ProductFull findById(String id);

  ProductFull create(CreateProduct dto);

  ProductFull update(String id, UpdateProduct dto);

  void delete(String id);
}
