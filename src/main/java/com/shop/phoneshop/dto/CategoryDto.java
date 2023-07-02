package com.shop.phoneshop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
    @ApiModelProperty(notes = "ID категории", required = true)
    private Long id;

    @ApiModelProperty(notes = "Название категории", required = true)
    private String title;

    @ApiModelProperty(notes = "Подкатегории данной категории")
    private List<SubcategoryDto> subcategoryDtos;
}
