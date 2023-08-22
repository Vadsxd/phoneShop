package com.shop.phoneshop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PropertyDto {
    @ApiModelProperty(notes = "ID свойства", required = true)
    private Long id;

    @ApiModelProperty(notes = "Название свойства", required = true)
    private String name;

    @ApiModelProperty(notes = "Значение свойства", required = true)
    private String value;
}
