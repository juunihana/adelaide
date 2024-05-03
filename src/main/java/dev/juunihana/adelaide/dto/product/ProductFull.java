package dev.juunihana.adelaide.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dev.juunihana.adelaide.dto.category.CategoryCompact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class ProductFull {

  private String id;

  private String name;

  private String description;

  private Double price;

  private CategoryCompact category;
}
