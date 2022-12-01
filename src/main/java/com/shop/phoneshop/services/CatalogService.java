package com.shop.phoneshop.services;

import com.shop.phoneshop.domain.Category;
import com.shop.phoneshop.domain.Product;
import com.shop.phoneshop.domain.Subcategory;
import com.shop.phoneshop.dto.ProductDto;
import com.shop.phoneshop.mappers.ProductMapper;
import com.shop.phoneshop.repos.CategoryRepo;
import com.shop.phoneshop.repos.ProductRepo;
import com.shop.phoneshop.repos.SubcategoryRepo;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final SubcategoryRepo subcategoryRepo;

    @Autowired
    public CatalogService(CategoryRepo categoryRepo, ProductRepo productRepo, SubcategoryRepo subcategoryRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.subcategoryRepo = subcategoryRepo;
    }

    public List<ProductDto> getAllProducts(JwtAuthentication authentication) {
        List<Product> products = productRepo.findAll();

        return ProductMapper.fromProductsToDtos(products, authentication);
    }

    public ProductDto getProduct(Long id, JwtAuthentication authentication) {
        Product product = productRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

        return ProductMapper.fromProductToDto(product, authentication);
    }

    public List<ProductDto> getAllProductsFromCategory(String title, JwtAuthentication authentication) {
        Category category = categoryRepo.findByTitle(title).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товары из данной категории не найдены"));
        List<Product> products = category.getSubcategories().stream()
                .flatMap(s -> s.getProducts().stream())
                .collect(Collectors.toList());

        return ProductMapper.fromProductsToDtos(products, authentication);
    }

    public List<ProductDto> getAllProductsFromSubcategory(String title, JwtAuthentication authentication) {
        Subcategory subcategory = subcategoryRepo.findByTitle(title).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товары из данной подкатегории не найдены"));
        List<Product> products = new ArrayList<>(subcategory.getProducts());

        return ProductMapper.fromProductsToDtos(products, authentication);
    }

    public List<ProductDto> getSmartphonesExtraProducts(JwtAuthentication authentication) {
        List<Product> headphones = subcategoryRepo.findByTitle("Headphones").orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Товары из данной подкатегории не найдены"))
                .getProducts();
        List<Product> covers = subcategoryRepo.findByTitle("SmartphoneCovers").orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Товары из данной подкатегории не найдены"))
                .getProducts();
        List<Product> products = new ArrayList<>(headphones);
        products.addAll(covers);

        return ProductMapper.fromProductsToDtos(products, authentication);
    }
}
