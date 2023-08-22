package com.shop.phoneshop.responses;

import com.shop.phoneshop.dto.PropertyDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductResponse {
    @ApiModelProperty(notes = "ID продукта", required = true)
    private Long id;

    @ApiModelProperty(notes = "ID категории товара", required = true)
    private Long categoryId;

    @ApiModelProperty(notes = "ID подкатегории товара", required = true)
    private Long subcategoryId;

    @ApiModelProperty(notes = "Ссылка на картинку для товара", required = true)
    private String pictureUrl;

    @ApiModelProperty(notes = "Название товара", required = true)
    private String title;

    @ApiModelProperty(notes = "Описание товара", required = true)
    private String description;

    @ApiModelProperty(notes = "Количество товара", required = true)
    private Long amount;

    @ApiModelProperty(notes = "Цена на товар", required = true)
    private Long price;

    @ApiModelProperty(notes = "Цена на товар с учетом скидки", required = true)
    private Long discountPrice;

    @ApiModelProperty(notes = "Свойства товара", required = true)
    private List<PropertyDto> properties;
}
