package com.shop.phoneshop.requests.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class MoveSubcategoryRequest {
    @NotNull
    @ApiModelProperty(value = "Идентификатор подкатегории, которую нужно переместить", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "Идентификатор родительской подкатегории, в которую нужно переместить", required = true)
    private Long destId;
}
