package com.shop.phoneshop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CatalogDto {
    @ApiModelProperty(notes = "Продукты в каталоге", required = true)
    List<ProductDto> productDtos;

    @ApiModelProperty(notes = "Количество товаров в категории", required = true)
    private Long categoryCount;

    @ApiModelProperty(notes = "Количество товаров в корзине", required = true)
    private Long cartCount;
}