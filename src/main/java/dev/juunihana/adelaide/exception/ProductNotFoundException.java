package dev.juunihana.adelaide.exception;

public class ProductNotFoundException extends NotFoundException {

  public ProductNotFoundException(String id) {
    super("Product not found with id " + id);
  }
}
