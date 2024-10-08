package dev.juunihana.adelaide.dto.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.juunihana.adelaide.dto.product.ProductFullDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CartDto {

    private String id;

    private List<ProductFullDto> products;

    private Double totalCost;
}
