package com.shop.phoneshop.mappers;

import com.shop.phoneshop.domain.UserProduct;
import com.shop.phoneshop.dto.UserProductDto;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import com.shop.phoneshop.utils.ProductUtil;

import java.util.List;
import java.util.stream.Collectors;

public interface UserProductMapper {
    static UserProductDto fromUserProductToDto(UserProduct userProduct, JwtAuthentication authentication) {
        UserProductDto userProductDto = new UserProductDto();
        userProductDto.setProductId(userProduct.getProduct().getId());
        userProductDto.setPictureUrl(userProduct.getProduct().getPictureUrl());
        userProductDto.setTitle(userProduct.getProduct().getTitle());
        userProductDto.setPrice(ProductUtil.getPrice(userProduct.getProduct(), authentication));
        userProductDto.setAmount(userProduct.getAmount());

        return userProductDto;
    }

    static List<UserProductDto> fromUserProductsToDtos(List<UserProduct> userProducts, JwtAuthentication authentication) {
        return userProducts.stream()
                .map(userProduct -> UserProductMapper.fromUserProductToDto(userProduct, authentication))
                .collect(Collectors.toList());
    }
}
