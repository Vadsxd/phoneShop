package com.shop.phoneshop.services;

import com.shop.phoneshop.domain.Category;
import com.shop.phoneshop.domain.Product;
import com.shop.phoneshop.domain.Subcategory;
import com.shop.phoneshop.repos.CategoryRepo;
import com.shop.phoneshop.repos.ProductRepo;
import com.shop.phoneshop.repos.SubcategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private  final SubcategoryRepo subcategoryRepo;

    @Autowired
    public AdminService(ProductRepo productRepo,
                        CategoryRepo categoryRepo,
                        SubcategoryRepo subcategoryRepo
    ) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.subcategoryRepo = subcategoryRepo;
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

        productRepo.delete(product);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Данной категории не существует"));

        categoryRepo.delete(category);
    }

    @Transactional
    public void deleteSubcategory(Long subcategoryId) {
        Subcategory subcategory = subcategoryRepo.findById(subcategoryId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Данной подкатегории не существует"));

        subcategoryRepo.delete(subcategory);
    }
}
