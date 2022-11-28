package com.shop.phoneshop.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartProductRequest {
    @ApiModelProperty(notes = "Id товара в корзине", required = true)
    @NotNull
    private Long productId;
}
