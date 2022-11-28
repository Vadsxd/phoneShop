package com.shop.phoneshop.services;

import com.shop.phoneshop.domain.*;
import com.shop.phoneshop.dto.CatalogDto;
import com.shop.phoneshop.dto.ProductDto;
import com.shop.phoneshop.mappers.CatalogMapper;
import com.shop.phoneshop.mappers.ProductMapper;
import com.shop.phoneshop.repos.*;
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
    private final UserRepo userRepo;
    private final UserProductRepo userProductRepo;

    @Autowired
    public CatalogService(CategoryRepo categoryRepo, ProductRepo productRepo,
                          SubcategoryRepo subcategoryRepo, UserRepo userRepo, UserProductRepo userProductRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.subcategoryRepo = subcategoryRepo;
        this.userRepo = userRepo;
        this.userProductRepo = userProductRepo;
    }

    public CatalogDto getAllProducts(JwtAuthentication authentication) {
        List<Product> products = productRepo.findAll();
        return formingCatalogDto(authentication, products);
    }

    private CatalogDto formingCatalogDto(JwtAuthentication authentication, List<Product> products) {
        List<ProductDto> productDtos = ProductMapper.fromProductsToDtos(products, authentication);

        User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        List<UserProduct> userProducts = userProductRepo.findAllByUser(user);

        return CatalogMapper.fromProductDtosToCatalogDto(productDtos, userProducts);
    }

    public CatalogDto getProduct(Long id, JwtAuthentication authentication) {
        Product product = productRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Продукт не найден"));

        ProductDto productDto = ProductMapper.fromProductToDto(product, authentication);

        User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        List<UserProduct> userProducts = userProductRepo.findAllByUser(user);

        return CatalogMapper.fromProductDtoToCatalogDto(productDto, userProducts);
    }

    public CatalogDto getAllProductsFromCategory(String title, JwtAuthentication authentication) {
        Category category = categoryRepo.findByTitle(title).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товары из данной категории не найдены"));
        List<Product> products = category.getSubcategories().stream()
                .flatMap(s -> s.getProducts().stream())
                .collect(Collectors.toList());

        return formingCatalogDto(authentication, products);
    }

    public CatalogDto getAllProductsFromSubcategory(String title, JwtAuthentication authentication) {
        Subcategory subcategory = subcategoryRepo.findByTitle(title).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товары из данной подкатегории не найдены"));
        List<Product> products = new ArrayList<>(subcategory.getProducts());

        return formingCatalogDto(authentication, products);
    }

    public CatalogDto getSmartphonesExtraProducts(JwtAuthentication authentication) {
        List<Product> headphones = subcategoryRepo.findByTitle("Headphones").orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Товары из данной подкатегории не найдены"))
                .getProducts();
        List<Product> covers = subcategoryRepo.findByTitle("SmartphoneCovers").orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Товары из данной подкатегории не найдены"))
                .getProducts();
        List<Product> products = new ArrayList<>(headphones);
        products.addAll(covers);

        return formingCatalogDto(authentication, products);
    }
}
