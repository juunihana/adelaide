package dev.juunihana.adelaide.exception;

public class ItemNotFoundException extends NotFoundException {

  public ItemNotFoundException(String id) {
    super("Item not found with id " + id);
  }
}
