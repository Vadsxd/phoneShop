package com.shop.phoneshop.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartProductRequest {
    @NotNull
    private Long userProductId;
}
