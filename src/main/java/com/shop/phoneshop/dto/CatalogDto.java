package com.shop.phoneshop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CatalogDto {
    List<ProductDto> productDtos;
    private Long countOfProductsInCategory;
    private Long countOfProductsInCart;
}
