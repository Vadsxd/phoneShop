package com.shop.phoneshop.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddProductRequest {
    @ApiModelProperty(notes = "Id товара, который надо добавить", required = true)
    @NotNull
    private Long productId;
}
