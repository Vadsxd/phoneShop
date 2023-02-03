package com.shop.phoneshop.mappers;

import com.shop.phoneshop.domain.Category;
import com.shop.phoneshop.domain.Subcategory;
import com.shop.phoneshop.requests.admin.SubcategoryRequest;

public interface SubcategoryMapper {
    static Subcategory fromSubcategoryRequestToSubcategory(SubcategoryRequest request, Category category, Subcategory parentSubcategory) {
        Subcategory subcategory = new Subcategory();
        subcategory.setId(request.getId());
        subcategory.setCategory(category);
        subcategory.setParentSubcategory(parentSubcategory);
        subcategory.setTitle(request.getTitle());

        return subcategory;
    }
}
