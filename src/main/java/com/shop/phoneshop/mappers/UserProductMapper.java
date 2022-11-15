package com.shop.phoneshop.mappers;

import com.shop.phoneshop.domain.UserProduct;
import com.shop.phoneshop.dto.UserProductDto;

import java.util.List;
import java.util.stream.Collectors;

public interface UserProductMapper {
    static UserProductDto fromUserProductToDto(UserProduct userProduct) {
        UserProductDto userProductDto = new UserProductDto();
        userProductDto.setPictureUrl(userProduct.getProduct().getPictureUrl());
        userProductDto.setTitle(userProduct.getProduct().getTitle());
        userProductDto.setPrice(userProduct.getProduct().getPrice());
        userProductDto.setAmount(userProduct.getAmount());

        return userProductDto;
    }

    static List<UserProductDto> fromUserProductsToDtos(List<UserProduct> userProducts) {
        return userProducts.stream()
                .map(UserProductMapper::fromUserProductToDto)
                .collect(Collectors.toList());
    }
}
