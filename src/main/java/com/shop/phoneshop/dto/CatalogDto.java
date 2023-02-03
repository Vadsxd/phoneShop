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
    @ApiModelProperty(notes = "Инициалы пользователя (null, если пользователь не авторизован)")
    private String initials;

    @ApiModelProperty(notes = "Список товаров", required = true)
    private List<ProductDto> productDtos;

    @ApiModelProperty(notes = "Количество товаров в данной категории/подкатегории", required = true)
    private Long categoryCount;

    @ApiModelProperty(notes = "Количество товаров, добавленных в корзину", required = true)
    private Long cartCount;
}