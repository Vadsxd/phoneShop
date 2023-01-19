package com.shop.phoneshop.requests.admin;

import com.shop.phoneshop.utils.validation.Marker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class SubcategoryRequest {
    @Null(groups = Marker.onCreate.class)
    @NotNull(groups = Marker.onUpdate.class)
    @ApiModelProperty(value = "Идентификатор подкатегории. Указывается при редактировании", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "ID категории, в которую входит данная подкатегория", required = true)
    private Long categoryId;

    @ApiModelProperty(value = "ID родительской подкатегории (null, если родителя нет)")
    private Long parentId;

    @NotBlank
    @Size(max = 255)
    @ApiModelProperty(value = "Название подкатегории", required = true)
    private String title;
}
