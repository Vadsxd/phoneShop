package com.shop.phoneshop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProductDto {
    private String pictureUrl;
    private String title;
    private Long price;
}
