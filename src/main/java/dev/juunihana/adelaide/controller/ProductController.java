package dev.juunihana.adelaide.controller;

import dev.juunihana.adelaide.dto.product.CreateProduct;
import dev.juunihana.adelaide.dto.product.ProductFull;
import dev.juunihana.adelaide.dto.product.UpdateProduct;
import dev.juunihana.adelaide.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping("/{id}")
  public ProductFull findById(@PathVariable String id) {
    return productService.findById(id);
  }

  @PostMapping("/new")
  @ResponseStatus(HttpStatus.CREATED)
  public ProductFull create(@RequestBody CreateProduct dto) {
    return productService.create(dto);
  }

  @PutMapping("/{id}")
  public ProductFull create(@PathVariable String id, @RequestBody UpdateProduct dto) {
    return productService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void create(@PathVariable String id) {
    productService.delete(id);
  }
}
