package dev.juunihana.adelaide.service;

import dev.juunihana.adelaide.dto.cart.CartDto;
import dev.juunihana.adelaide.dto.product.ProductCompactDto;

public interface CartService {

  CartDto get();

  ProductCompactDto addProductToCart(String productId);
}
