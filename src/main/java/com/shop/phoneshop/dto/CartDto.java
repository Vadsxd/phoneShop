package com.shop.phoneshop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartDto {
    @ApiModelProperty(notes = "Список товаров в корзине", required = true)
    private List<UserProductDto> userProductDtos;

    @ApiModelProperty(notes = "Количество товаров в корзине", required = true)
    private Long count;

    @ApiModelProperty(notes = "Общая цена товаров в корзине", required = true)
    private Long fullPrice;
}
