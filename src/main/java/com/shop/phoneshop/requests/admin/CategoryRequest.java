package com.shop.phoneshop.requests.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.shop.phoneshop.utils.validation.Marker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequest {
    @Null(groups = Marker.onCreate.class)
    @NotNull(groups = Marker.onUpdate.class)
    @ApiModelProperty(value = "Идентификатор категории. Указывается при редактировании", required = true)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @ApiModelProperty(value = "Название категории", required = true)
    private String title;
}
