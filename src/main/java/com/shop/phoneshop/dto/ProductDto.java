package com.shop.phoneshop.dto;

import com.shop.phoneshop.domain.enums.ProductStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
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

    @ApiModelProperty(notes = "Статус товара (AVAILABLE - большое количество, LOW - мало, EMPTY - товар отсутствует)",
            required = true)
    private ProductStatus status;

    @ApiModelProperty(notes = "Цена на товар", required = true)
    private Long price;

    @ApiModelProperty(notes = "Отзывы товара", required = true)
    private List<UserFeedbackDto> userFeedbackDtos;
}
