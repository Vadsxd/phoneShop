package com.shop.phoneshop.services;

import com.shop.phoneshop.domain.Category;
import com.shop.phoneshop.domain.Product;
import com.shop.phoneshop.domain.Subcategory;
import com.shop.phoneshop.mappers.CategoryMapper;
import com.shop.phoneshop.mappers.SubcategoryMapper;
import com.shop.phoneshop.repos.CategoryRepo;
import com.shop.phoneshop.repos.ProductRepo;
import com.shop.phoneshop.repos.SubcategoryRepo;
import com.shop.phoneshop.requests.admin.CategoryRequest;
import com.shop.phoneshop.requests.admin.MoveSubcategoryRequest;
import com.shop.phoneshop.requests.admin.MoveSubcategoryToCategoryRequest;
import com.shop.phoneshop.requests.admin.SubcategoryRequest;
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
    public Category addCategory(CategoryRequest request) {
        Category category = CategoryMapper.fromCategoryRequestToCategory(request);
        categoryRepo.save(category);

        return category;
    }

    @Transactional
    public void updateCategory(CategoryRequest request) {
        Long categoryId = request.getId();
        Category category = categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Данной категории не существует"));

        category.setTitle(request.getTitle());
        categoryRepo.save(category);
    }

    @Transactional
    public Subcategory addSubcategory(SubcategoryRequest request) {
        Long categoryId = request.getCategoryId();
        Long parentId = request.getParentId();
        Subcategory parentSubcategory = null;

        Category category = categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Данной категории не существует"));

        if (parentId != 0) {
            parentSubcategory = subcategoryRepo.findById(parentId).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Данной родительской подкатегории не существует"));
        }

        Subcategory subcategory = SubcategoryMapper.fromSubcategoryRequestToSubcategory(request, category, parentSubcategory);
        subcategoryRepo.save(subcategory);

        return subcategory;
    }

    @Transactional
    public void updateSubcategory(SubcategoryRequest request) {
        Long categoryId = request.getId();
        Long parentId = request.getParentId();
        Subcategory parentSubcategory = null;
        Subcategory subcategory = subcategoryRepo.findById(parentId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Данной подкатегории не существует"));

        Category category = categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Данной категории не существует"));

        if (parentId != 0) {
            parentSubcategory = subcategoryRepo.findById(parentId).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Данной родительской подкатегории не существует"));
        }

        subcategory.setSubcategory(parentSubcategory);
        subcategory.setCategory(category);
        subcategory.setTitle(request.getTitle());
        subcategoryRepo.save(subcategory);
    }

    @Transactional
    public void moveSubcategoryToCategory(MoveSubcategoryToCategoryRequest request) {
        Long subId = request.getSubId();
        Long catId = request.getCatId();

        Subcategory subcategory = subcategoryRepo.findById(subId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Данной подкатегории не существует"));

        Category category = categoryRepo.findById(catId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Данной категории не существует"));

        subcategory.setCategory(category);
        subcategoryRepo.save(subcategory);
    }

    @Transactional
    public void moveSubcategory(MoveSubcategoryRequest request) {
        Long id = request.getId();
        Long destId = request.getDestId();

        Subcategory subcategory = subcategoryRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Данной подкатегории не существует"));

        Subcategory destSubcategory = subcategoryRepo.findById(destId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Данной родительской подкатегории не существует"));

        subcategory.setSubcategory(destSubcategory);
        subcategoryRepo.save(subcategory);
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
