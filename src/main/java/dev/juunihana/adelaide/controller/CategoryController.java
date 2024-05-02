package dev.juunihana.adelaide.controller;

import dev.juunihana.adelaide.dto.Category;
import dev.juunihana.adelaide.service.CategoryService;
import java.util.List;
import java.util.UUID;
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
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("/{id}")
  public Category findById(@PathVariable String id) {
    return categoryService.findById(id);
  }

  @GetMapping("/all")
  public List<Category> findById() {
    return categoryService.findAll();
  }

  @PostMapping("/new")
  @ResponseStatus(HttpStatus.CREATED)
  public Category create(@RequestBody Category category) {
    return categoryService.create(category);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable String id) {
    categoryService.delete(id);
  }
}
