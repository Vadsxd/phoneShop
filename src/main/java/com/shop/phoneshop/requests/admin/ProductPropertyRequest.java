package com.shop.phoneshop.requests.admin;

import com.shop.phoneshop.utils.validation.Marker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductPropertyRequest {
    @Null(groups = Marker.onCreate.class)
    @NotNull(groups = Marker.onUpdate.class)
    @ApiModelProperty(value = "Идентификатор свойств продукта. Указывается при редактировании", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "Идентификаторы конкретных свойств продукта", required = true)
    private List<Long> propertyIds;
}
