package com.shop.phoneshop.dto;

import com.shop.phoneshop.domain.enums.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    private String pictureUrl;
    private String title;
    private String description;
    private ProductStatus status;
    private Long price;
}
