package dev.juunihana.adelaide.service.impl;

import dev.juunihana.adelaide.dto.cart.CartDto;
import dev.juunihana.adelaide.dto.cart.ProductCartDto;
import dev.juunihana.adelaide.entity.CartEntity;
import dev.juunihana.adelaide.entity.ProductEntity;
import dev.juunihana.adelaide.exception.BadRequestException;
import dev.juunihana.adelaide.exception.NotAuthorizedException;
import dev.juunihana.adelaide.exception.NotFoundException;
import dev.juunihana.adelaide.mapper.CartMapper;
import dev.juunihana.adelaide.repository.CartRepository;
import dev.juunihana.adelaide.service.CartService;
import dev.juunihana.adelaide.service.ProductService;
import dev.juunihana.adelaide.service.UserService;
import dev.juunihana.adelaide.util.Errors;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartMapper cartMapper = Mappers.getMapper(CartMapper.class);

  private final CartRepository cartRepository;

  private final ProductService productService;

  private final UserService userService;

  private CartEntity getCurrentUserCartEntity() {
    return cartRepository.findByUserEmail(userService.getCurrentUserEmail())
        .orElseThrow(() -> new NotFoundException(
            MessageFormat.format(Errors.CART_NOT_FOUND_EMAIL,
                userService.getCurrentUserEmail())));
  }

  @Override
  public CartDto getCurrentUserCart() {
    return cartMapper.cartEntityToDto(getCurrentUserCartEntity());
  }

  @Override
  public void create() {
    if (userService.isUserNotAuthorized()) {
      throw new NotAuthorizedException(Errors.NOT_AUTHORIZED);
    }

    if (cartRepository.findByUserEmail(userService.getCurrentUserEmail()).isEmpty()) {
      cartMapper.cartEntityToDto(cartRepository.save(
          CartEntity.builder()
              .user(userService.getCurrentUserEntity())
              .totalCost(0.0)
              .createTime(LocalDateTime.now())
              .updateTime(LocalDateTime.now())
              .build()));
    } else {
      throw new BadRequestException(
          MessageFormat.format(Errors.CART_EXISTS, userService.getCurrentUserEmail()));
    }
  }

  @Override
  public void addProductToCart(ProductCartDto productCartDto) {
    CartEntity entity = getCurrentUserCartEntity();

    if (entity.getProducts() == null) {
      entity.setProducts(new ArrayList<>());
    }

    ProductEntity product = productService.findEntityById(productCartDto.getProductId());

    entity.getProducts().add(product);
    entity.setTotalCost(entity.getProducts().stream().mapToDouble(ProductEntity::getPrice).sum());
    entity.setUpdateTime(LocalDateTime.now());

    cartRepository.save(entity);
  }

  @Override
  public void removeProductFromCart(ProductCartDto productCartDto) {
    CartEntity entity = getCurrentUserCartEntity();

    ProductEntity product = productService.findEntityById(productCartDto.getProductId());

    entity.getProducts().remove(product);
    entity.setTotalCost(entity.getProducts().stream().mapToDouble(ProductEntity::getPrice).sum());
    entity.setUpdateTime(LocalDateTime.now());

    cartRepository.save(entity);
  }

  @Override
  public void delete() {
    cartRepository.delete(getCurrentUserCartEntity());
  }
}
