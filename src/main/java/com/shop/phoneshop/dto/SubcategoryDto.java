package com.shop.phoneshop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SubcategoryDto {
    @ApiModelProperty(notes = "ID подкатегории", required = true)
    private Long id;

    @ApiModelProperty(notes = "Название подкатегории", required = true)
    private String title;

    @ApiModelProperty(notes = "Название подкатегории")
    private List<SubcategoryDto> childSubcategoriesDtos;
}
