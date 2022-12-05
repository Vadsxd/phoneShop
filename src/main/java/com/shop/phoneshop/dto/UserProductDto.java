package com.shop.phoneshop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProductDto {
    @ApiModelProperty(notes = "Ссылка на картинку для товара", required = true)
    private String pictureUrl;

    @ApiModelProperty(notes = "Название товара", required = true)
    private String title;

    @ApiModelProperty(notes = "Цена на товар", required = true)
    private Long price;

    @ApiModelProperty(notes = "Количество товара в магазине", required = true)
    private Long amount;
}
