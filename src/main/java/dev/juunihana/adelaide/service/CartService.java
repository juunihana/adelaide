package dev.juunihana.adelaide.service;

import dev.juunihana.adelaide.dto.cart.CartDto;
import dev.juunihana.adelaide.dto.cart.ProductCartDto;

public interface CartService {

  CartDto getCurrentUserCart();

  void create();

  void addProductToCart(ProductCartDto productCartDto);

  void removeProductFromCart(ProductCartDto productCartDto);

  void delete();
}
