package dev.juunihana.adelaide.exception;

public class CategoryNotFoundException extends NotFoundException {

  public CategoryNotFoundException(String id) {
    super("Category not found with id " + id);
  }
}
