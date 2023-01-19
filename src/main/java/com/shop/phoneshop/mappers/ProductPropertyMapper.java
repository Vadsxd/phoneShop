package com.shop.phoneshop.mappers;

import com.shop.phoneshop.domain.ProductProperty;
import com.shop.phoneshop.domain.Property;
import com.shop.phoneshop.requests.admin.ProductPropertyRequest;

import java.util.List;

public interface ProductPropertyMapper {
    static ProductProperty fromProductPropertyRequestToProductProperty(ProductPropertyRequest request, List<Property> properties) {
        ProductProperty productProperty = new ProductProperty();
        productProperty.setId(request.getId());
        productProperty.setProperties(properties);

        return productProperty;
    }
}
