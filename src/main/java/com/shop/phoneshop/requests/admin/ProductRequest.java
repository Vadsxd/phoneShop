package com.shop.phoneshop.requests.admin;

import com.shop.phoneshop.utils.validation.Marker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {
    @Null(groups = Marker.onCreate.class)
    @NotNull(groups = Marker.onUpdate.class)
    @ApiModelProperty(value = "Идентификатор продукта. Указывается при редактировании", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "ID подкатегории, в которую входит данный продукт", required = true)
    private Long subcategoryId;

    @NotBlank
    @Size(max = 255)
    @ApiModelProperty(value = "Название продукта", required = true)
    private String title;

    @NotBlank
    @Size(max = 500)
    @ApiModelProperty(value = "Описание продукта", required = true)
    private String description;

    @NotNull
    @ApiModelProperty(value = "Количество продукта", required = true)
    private Long amount;

    @NotBlank
    @ApiModelProperty(value = "Ссылка на фотографию", required = true)
    private String pictureUrl;

    @NotNull
    @ApiModelProperty(value = "Цена товара", required = true)
    private Long price;

    @NotNull
    @ApiModelProperty(value = "Цена товара по скидке (null, если скидки нет)")
    private Long discountPrice;

    @NotNull
    @ApiModelProperty(value = "Наличие скидки", required = true)
    private Boolean discount;

    @NotNull
    @ApiModelProperty(value = "ID набора свойств товара", required = true)
    private Long productPropertyId;
}
