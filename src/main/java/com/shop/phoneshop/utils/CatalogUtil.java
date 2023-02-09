package com.shop.phoneshop.utils;

import com.shop.phoneshop.domain.Product;
import com.shop.phoneshop.domain.Subcategory;
import com.shop.phoneshop.domain.User;

import java.util.List;

public interface CatalogUtil {
    static String getInitials(User user) {
        if (user != null) {
            return user.getLastName() + ". " + user.getFirstName().charAt(0);
        }
        return null;
    }

    static void getProductsFromSubcategoryAndChilds(List<Product> products, List<Subcategory> childSubcategories) {
        products.addAll(childSubcategories.stream()
                .flatMap(s -> s.getProducts().stream())
                .toList());

        for(Subcategory subcategory : childSubcategories) {
            getProductsFromSubcategoryAndChilds(products, subcategory.getChildSubcategories());
        }
    }
}
