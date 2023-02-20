package com.shop.phoneshop.requests.admin;

import com.shop.phoneshop.utils.validation.Marker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@NoArgsConstructor
public class AddPropertyRequest {
    @Null(groups = Marker.onCreate.class)
    @NotNull(groups = Marker.onUpdate.class)
    @ApiModelProperty(value = "Идентификатор свойства", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "Идентификатор набора свойств для продукта", required = true)
    private Long prodPropId;

    @NotBlank
    @ApiModelProperty(value = "Название свойства", required = true)
    private String name;

    @NotBlank
    @ApiModelProperty(value = "Значение свойства", required = true)
    private String value;
}
