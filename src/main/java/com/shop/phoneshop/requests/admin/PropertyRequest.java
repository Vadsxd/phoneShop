package com.shop.phoneshop.requests.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PropertyRequest {
    @NotNull
    @ApiModelProperty(value = "Идентификатор свойства", required = true)
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "Название свойства", required = true)
    private String name;

    @NotBlank
    @ApiModelProperty(value = "Значение свойства", required = true)
    private String value;
}
