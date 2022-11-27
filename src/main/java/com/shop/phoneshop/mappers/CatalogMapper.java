package com.shop.phoneshop.mappers;

import com.shop.phoneshop.domain.UserProduct;
import com.shop.phoneshop.dto.CatalogDto;
import com.shop.phoneshop.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public interface CatalogMapper {
    static CatalogDto fromProductDtosAndUserProductsToCatalogDto(List<ProductDto> productDtos, List<UserProduct> userProducts) {
        CatalogDto catalogDto = new CatalogDto();

        catalogDto.setProductDtos(productDtos);

        catalogDto.setCountOfProductsInCategory((long) productDtos.size());

        catalogDto.setCountOfProductsInCart(userProducts.stream().
                flatMapToLong(a -> LongStream.of(a.getAmount()))
                .sum());

        return catalogDto;
    }

    static CatalogDto fromProductDtoAndUserProductsToCatalogDto(ProductDto productDto, List<UserProduct> userProducts) {
        CatalogDto catalogDto = new CatalogDto();

        List<ProductDto> productDtos = new ArrayList<>();
        productDtos.add(productDto);
        catalogDto.setProductDtos(productDtos);

        catalogDto.setCountOfProductsInCategory((long) productDtos.size());

        catalogDto.setCountOfProductsInCart(userProducts.stream().
                flatMapToLong(a -> LongStream.of(a.getAmount()))
                .sum());

        return catalogDto;
    }
}
