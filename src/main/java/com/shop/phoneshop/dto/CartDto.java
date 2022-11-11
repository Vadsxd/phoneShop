package com.shop.phoneshop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartDto {
    List<UserProductDto> userProductDtos;
    private Long count;
    private Long fullPrice;
}
