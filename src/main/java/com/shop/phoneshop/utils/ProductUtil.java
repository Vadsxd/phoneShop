package com.shop.phoneshop.utils;

import com.shop.phoneshop.domain.Product;
import com.shop.phoneshop.domain.enums.ProductStatus;
import com.shop.phoneshop.security.jwt.JwtAuthentication;

public interface ProductUtil {
    static Long getPrice(Product product, JwtAuthentication authentication) {
        if (authentication != null && product.getDiscount()) {
            return product.getDiscountPrice();
        }
        return product.getPrice();
    }

    static ProductStatus getStatus(Product product) {
        if (product.getAmount() > 5) {
            return ProductStatus.AVAILABLE;
        }
        else if (product.getAmount() <= 5 && product.getAmount() >= 1) {
            return ProductStatus.LOW;
        }
        else {
            return ProductStatus.EMPTY;
        }
    }
}
