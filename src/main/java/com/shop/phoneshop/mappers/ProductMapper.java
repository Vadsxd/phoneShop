package com.shop.phoneshop.mappers;

import com.shop.phoneshop.domain.Product;
import com.shop.phoneshop.dto.ProductDto;
import com.shop.phoneshop.dto.UserFeedbackDto;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import com.shop.phoneshop.utils.ProductUtil;

import java.util.List;
import java.util.stream.Collectors;

public interface ProductMapper {
    static ProductDto fromProductToDto(Product product, JwtAuthentication authentication,
                                       List<UserFeedbackDto> userFeedbackDtos) {
        ProductDto productDto = new ProductDto();
        productDto.setPictureUrl(product.getPictureUrl());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setStatus(ProductUtil.getStatus(product));
        productDto.setPrice(ProductUtil.getPrice(product, authentication));
        productDto.setUserFeedbackDtos(userFeedbackDtos);

        return productDto;
    }

//    static List<ProductDto> fromProductsToDtos(List<Product> products, JwtAuthentication authentication) {
//        return products.stream()
//                .map(product -> ProductMapper.fromProductToDto(product, authentication))
//                .collect(Collectors.toList());
//    }
}
