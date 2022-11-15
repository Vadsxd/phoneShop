package com.shop.phoneshop.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddProductRequest {
    @NotNull
    private Long productId;
}
