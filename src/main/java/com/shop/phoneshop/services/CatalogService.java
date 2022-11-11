package com.shop.phoneshop.services;

import com.shop.phoneshop.domain.Category;
import com.shop.phoneshop.domain.Product;
import com.shop.phoneshop.dto.ProductDto;
import com.shop.phoneshop.mappers.ProductMapper;
import com.shop.phoneshop.repos.CategoryRepo;
import com.shop.phoneshop.repos.ProductRepo;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;

    public CatalogService(CategoryRepo categoryRepo, ProductRepo productRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
    }

    public List<ProductDto> getAllProducts(JwtAuthentication authentication) {
        List<Product> products = productRepo.findAll();
        return ProductMapper.fromProductsToDtos(products, authentication);
    }

    public List<ProductDto> getAllProductsFromCategory(String title, JwtAuthentication authentication) {
        List<Category> categories = categoryRepo.findAllByTitle(title);
        List<Product> products = categories.stream()
                .flatMap(c -> c.getSubcategories().stream())
                .flatMap(s -> s.getProducts().stream())
                .collect(Collectors.toList());
        return ProductMapper.fromProductsToDtos(products, authentication);
    }
}
