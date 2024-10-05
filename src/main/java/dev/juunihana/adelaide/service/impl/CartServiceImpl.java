package dev.juunihana.adelaide.service.impl;

import dev.juunihana.adelaide.dto.cart.CartDto;
import dev.juunihana.adelaide.dto.product.ProductCompactDto;
import dev.juunihana.adelaide.exception.NotFoundException;
import dev.juunihana.adelaide.mapper.CartMapper;
import dev.juunihana.adelaide.repository.CartRepository;
import dev.juunihana.adelaide.service.CartService;
import dev.juunihana.adelaide.service.ProductService;
import dev.juunihana.adelaide.util.Errors;
import java.text.MessageFormat;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartMapper cartMapper = Mappers.getMapper(CartMapper.class);

  private final CartRepository cartRepository;

  private final ProductService productService;

  @Override
  public CartDto get() {
    return cartMapper.cartEntityToDto(cartRepository.findByUserId(UUID.randomUUID())
        .orElseThrow(() -> new NotFoundException(
            MessageFormat.format(Errors.CART_NOT_FOUND, UUID.randomUUID()))));
  }

  @Override
  public ProductCompactDto addProductToCart(String productId) {

    return null;
  }
}
