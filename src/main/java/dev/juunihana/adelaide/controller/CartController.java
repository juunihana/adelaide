package dev.juunihana.adelaide.controller;

import dev.juunihana.adelaide.dto.cart.CartDto;
import dev.juunihana.adelaide.dto.cart.ProductCartDto;
import dev.juunihana.adelaide.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  /**
   * Get cart for currently authorized user
   *
   * @return Cart - Cart DTO object
   */
  @GetMapping
  @PreAuthorize("hasRole('ROLE_USER')")
  public CartDto get() {
    return cartService.getCurrentUserCart();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_USER')")
  public void create() {
    cartService.create();
  }

  @PutMapping("/add")
  @PreAuthorize("hasRole('ROLE_USER')")
  public void addProductToCart(
      @RequestBody ProductCartDto productCartDto
  ) {
    cartService.addProductToCart(productCartDto);
  }

  @PutMapping("/remove")
  @PreAuthorize("hasRole('ROLE_USER')")
  public void removeProductFromCart(
      @RequestBody ProductCartDto productCartDto
  ) {
    cartService.removeProductFromCart(productCartDto);
  }
}
