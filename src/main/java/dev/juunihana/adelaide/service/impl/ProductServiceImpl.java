package dev.juunihana.adelaide.service.impl;

import dev.juunihana.adelaide.dto.product.CreateProductDto;
import dev.juunihana.adelaide.dto.product.ProductFull;
import dev.juunihana.adelaide.dto.product.UpdateProductDto;
import dev.juunihana.adelaide.entity.ProductEntity;
import dev.juunihana.adelaide.exception.NotFoundException;
import dev.juunihana.adelaide.mapper.ProductMapper;
import dev.juunihana.adelaide.repository.CategoryRepository;
import dev.juunihana.adelaide.repository.ProductRepository;
import dev.juunihana.adelaide.service.ProductService;
import dev.juunihana.adelaide.util.Errors;
import jakarta.transaction.Transactional;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  @Override
  public List<ProductFull> findAll() {
    return productRepository.findAll(PageRequest.of(1, 10)).stream()
        .map(productMapper::productEntityToProductFull)
        .toList();
  }

  @Override
  public ProductFull findById(String id) {
    return productMapper.productEntityToProductFull(productRepository.findById(UUID.fromString(id))
        .orElseThrow(
            () -> new NotFoundException(MessageFormat.format(Errors.PRODUCT_NOT_FOUND, id))));
  }

  @Override
  @Transactional
  public ProductFull create(CreateProductDto dto) {
    ProductEntity entity = productMapper.productCreateToProductEntity(dto);

    entity.setCategory(categoryRepository.findById(UUID.fromString(dto.getCategoryId()))
        .orElseThrow(() -> new NotFoundException(
            MessageFormat.format(Errors.CATEGORY_NOT_FOUND, dto.getCategoryId()))));

    return productMapper.productEntityToProductFull(productRepository.save(entity));
  }

  @Override
  @Transactional
  public ProductFull update(String id, UpdateProductDto dto) {
    ProductEntity entity = productRepository.findById(UUID.fromString(id))
        .orElseThrow(
            () -> new NotFoundException(MessageFormat.format(Errors.PRODUCT_NOT_FOUND, id)));

    productMapper.update(entity, dto);

    if (StringUtils.hasLength(dto.getCategoryId())) {
      entity.setCategory(categoryRepository.findById(UUID.fromString(dto.getCategoryId()))
          .orElseThrow(() -> new NotFoundException(
              MessageFormat.format(Errors.CATEGORY_NOT_FOUND, dto.getCategoryId()))));
    }

    return productMapper.productEntityToProductFull(productRepository.save(entity));
  }

  @Override
  @Transactional
  public void delete(String id) {
    productRepository.deleteById(UUID.fromString(id));
  }
}
