package dev.juunihana.adelaide.controller;

import dev.juunihana.adelaide.dto.category.CategoryFull;
import dev.juunihana.adelaide.dto.category.CreateCategory;
import dev.juunihana.adelaide.dto.category.UpdateCategory;
import dev.juunihana.adelaide.dto.product.ProductFull;
import dev.juunihana.adelaide.service.CategoryService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("/{id}")
  public CategoryFull findById(@PathVariable String id) {
    return categoryService.findById(id);
  }

  @GetMapping("/all")
  public List<CategoryFull> findById() {
    return categoryService.findAll();
  }

  @GetMapping("/{categoryId}/products")
  public Set<ProductFull> findProductsFromCategory(
      @PathVariable String categoryId,
      @RequestParam Integer pageNumber,
      @RequestParam(required = false) Integer pageSize) {
    return categoryService.findProductsFromCategory(categoryId, pageNumber, pageSize);
  }

  @PostMapping("/new")
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryFull create(@RequestBody CreateCategory category) {
    return categoryService.create(category);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryFull create(
      @PathVariable String id,
      @RequestBody UpdateCategory category) {
    return categoryService.update(id, category);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable String id) {
    categoryService.delete(id);
  }
}
