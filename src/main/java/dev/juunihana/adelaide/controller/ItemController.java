package dev.juunihana.adelaide.controller;

import dev.juunihana.adelaide.dto.item.CreateItem;
import dev.juunihana.adelaide.dto.item.ItemFull;
import dev.juunihana.adelaide.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/{id}")
  public ItemFull findById(@PathVariable String id) {
    return itemService.findById(id);
  }

  @PostMapping("/new")
  @ResponseStatus(HttpStatus.CREATED)
  public ItemFull create(@RequestBody CreateItem dto) {
    return itemService.create(dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void create(@PathVariable String id) {
    itemService.delete(id);
  }
}
