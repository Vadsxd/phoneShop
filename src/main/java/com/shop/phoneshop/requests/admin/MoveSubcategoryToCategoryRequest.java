package com.shop.phoneshop.requests.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class MoveSubcategoryToCategoryRequest {
    @NotNull
    @ApiModelProperty(value = "Идентификатор подкатегории, которую нужно переместить", required = true)
    private Long subId;

    @NotNull
    @ApiModelProperty(value = "Идентификатор категории, в которую нужно переместить", required = true)
    private Long catId;
}
