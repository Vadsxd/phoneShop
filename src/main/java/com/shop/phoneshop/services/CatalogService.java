package com.shop.phoneshop.services;

import com.shop.phoneshop.domain.Category;
import com.shop.phoneshop.domain.Product;
import com.shop.phoneshop.dto.ProductDto;
import com.shop.phoneshop.mappers.ProductMapper;
import com.shop.phoneshop.repos.CategoryRepo;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {
    private final CategoryRepo categoryRepo;

    public CatalogService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<ProductDto> getAllSmartphones(JwtAuthentication authentication) {
        List<Category> smartphoneCategories = categoryRepo.findAllByTitle("Smartphones");
        List<Product> smartphones = smartphoneCategories.stream()
                .flatMap(c -> c.getSubcategories().stream())
                .flatMap(s -> s.getProducts().stream())
                .collect(Collectors.toList());
        return ProductMapper.fromProductsToDtos(smartphones, authentication);
    }
}
