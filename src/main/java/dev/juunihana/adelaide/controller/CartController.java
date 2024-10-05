package dev.juunihana.adelaide.controller;

import dev.juunihana.adelaide.dto.cart.CartDto;
import dev.juunihana.adelaide.dto.product.ProductCompactDto;
import dev.juunihana.adelaide.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  /**
   * Get cart for current authorized user
   *
   * @return Cart - Cart DTO object
   */
  @GetMapping
  public CartDto get() {
    return cartService.get();
  }

  @PutMapping("/{productId}")
  public ProductCompactDto addProductToCart(
      @PathVariable String productId
  ) {
    return cartService.addProductToCart(productId);
  }
}
