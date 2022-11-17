package com.shop.phoneshop.mappers;

import com.shop.phoneshop.dto.CartDto;
import com.shop.phoneshop.dto.UserProductDto;

import java.util.List;
import java.util.stream.LongStream;

public interface CartMapper {
    static CartDto fromUserProductDtosToCartDto(List<UserProductDto> userProductDtos) {
        CartDto cartDto = new CartDto();
        cartDto.setUserProductDtos(userProductDtos);
        cartDto.setCount(userProductDtos.stream()
                .flatMapToLong(a -> LongStream.of(a.getAmount()))
                .sum());
        cartDto.setFullPrice(userProductDtos.stream()
                .flatMapToLong(p -> LongStream.of(p.getPrice() * p.getAmount()))
                .sum());

        return cartDto;
    }
}
