package com.shop.phoneshop.mappers;

import com.shop.phoneshop.domain.User;
import com.shop.phoneshop.dto.CatalogDto;
import com.shop.phoneshop.dto.ProductDto;
import com.shop.phoneshop.dto.UserProductDto;
import com.shop.phoneshop.utils.CatalogUtil;

import java.util.List;
import java.util.stream.LongStream;

public interface CatalogMapper {
    static CatalogDto fromProductDtosToCatalogDto(List<ProductDto> productDtos, List<UserProductDto> userProductDtos,
                                                  User user) {
        CatalogDto catalogDto = new CatalogDto();

        catalogDto.setInitials(CatalogUtil.getInitials(user));
        catalogDto.setProductDtos(productDtos);
        catalogDto.setCategoryCount((long) productDtos.size());
        catalogDto.setCartCount(userProductDtos.stream()
                .flatMapToLong(a -> LongStream.of(a.getAmount()))
                .sum());

        return catalogDto;
    }

//    static CatalogDto fromProductDtoToCatalogDto(ProductDto productDto, List<UserProduct> userProducts) {
//        CatalogDto catalogDto = new CatalogDto();
//
//        List<ProductDto> productDtos = new ArrayList<>();
//        productDtos.add(productDto);
//        catalogDto.setProductDtos(productDtos);
//        catalogDto.setCategoryCount((long) productDtos.size());
//        catalogDto.setCartCount(userProducts.stream().
//                flatMapToLong(a -> LongStream.of(a.getAmount()))
//                .sum());
//
//        return catalogDto;
//    }
}