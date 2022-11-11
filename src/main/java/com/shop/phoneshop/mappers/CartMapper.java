package com.shop.phoneshop.mappers;

import com.shop.phoneshop.dto.CartDto;
import com.shop.phoneshop.dto.UserProductDto;

import java.util.List;

public interface CartMapper {
    static CartDto fromUserProductDtosToCartDto(List<UserProductDto> userProductDtos) {
        CartDto cartDto = new CartDto();
        cartDto.setUserProductDtos(userProductDtos);
        cartDto.setCount((long) userProductDtos.size());
        cartDto.setFullPrice(userProductDtos.stream()
                .mapToLong(UserProductDto::getPrice)
                .sum());

        return cartDto;
    }
}
