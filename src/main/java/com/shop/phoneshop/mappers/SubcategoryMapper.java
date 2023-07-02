package com.shop.phoneshop.mappers;

import com.shop.phoneshop.domain.Category;
import com.shop.phoneshop.domain.Subcategory;
import com.shop.phoneshop.dto.SubcategoryDto;
import com.shop.phoneshop.requests.admin.SubcategoryRequest;

import java.util.List;
import java.util.stream.Collectors;

public interface SubcategoryMapper {
    static Subcategory fromSubcategoryRequestToSubcategory(SubcategoryRequest request, Category category, Subcategory parentSubcategory) {
        Subcategory subcategory = new Subcategory();
        subcategory.setId(request.getId());
        subcategory.setCategory(category);
        subcategory.setParentSubcategory(parentSubcategory);
        subcategory.setTitle(request.getTitle());

        return subcategory;
    }

    static SubcategoryDto fromSubcategoryToSubcategoryDto(Subcategory subcategory) {
        SubcategoryDto subcategoryDto = new SubcategoryDto();
        subcategoryDto.setId(subcategory.getId());
        subcategoryDto.setTitle(subcategory.getTitle());
        subcategoryDto.setChildSubcategoriesDtos(SubcategoryMapper.fromSubcategoriesToSubcategoriesDtos(
                subcategory.getChildSubcategories()));

        return subcategoryDto;
    }

    static List<SubcategoryDto> fromSubcategoriesToSubcategoriesDtos(List<Subcategory> subcategories) {
        return subcategories.stream()
                .map(SubcategoryMapper::fromSubcategoryToSubcategoryDto)
                .collect(Collectors.toList());
    }
}
