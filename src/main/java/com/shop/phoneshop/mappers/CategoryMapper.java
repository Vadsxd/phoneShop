package com.shop.phoneshop.mappers;

import com.shop.phoneshop.domain.Category;
import com.shop.phoneshop.requests.admin.CategoryRequest;

public interface CategoryMapper {
    static Category fromCategoryRequestToCategory(CategoryRequest request) {
        Category category = new Category();
        category.setId(request.getId());
        category.setTitle(request.getTitle());

        return category;
    }
}
