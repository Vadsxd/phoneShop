package com.shop.phoneshop.mappers;

import com.shop.phoneshop.domain.Category;
import com.shop.phoneshop.dto.CatalogDto;
import com.shop.phoneshop.dto.CategoryDto;
import com.shop.phoneshop.requests.admin.CategoryRequest;

import java.util.List;
import java.util.stream.Collectors;

public interface CategoryMapper {
    static Category fromCategoryRequestToCategory(CategoryRequest request) {
        Category category = new Category();
        category.setId(request.getId());
        category.setTitle(request.getTitle());

        return category;
    }

    static CategoryDto fromCategoryToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
        categoryDto.setSubcategoryDtos(SubcategoryMapper.fromSubcategoriesToSubcategoriesDtos(
                category.getSubcategories()));

        return categoryDto;
    }

    static List<CategoryDto> fromCategoriesToCategoriesDtos(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::fromCategoryToCategoryDto)
                .collect(Collectors.toList());
    }
}
