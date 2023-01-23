package com.shop.phoneshop.services;

import com.shop.phoneshop.domain.*;
import com.shop.phoneshop.mappers.CategoryMapper;
import com.shop.phoneshop.mappers.ProductMapper;
import com.shop.phoneshop.mappers.ProductPropertyMapper;
import com.shop.phoneshop.mappers.SubcategoryMapper;
import com.shop.phoneshop.repos.*;
import com.shop.phoneshop.requests.admin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final SubcategoryRepo subcategoryRepo;
    private final ProductPropertyRepo productPropertyRepo;
    private final PropertyRepo propertyRepo;

    @Autowired
    public AdminService(ProductRepo productRepo,
                        CategoryRepo categoryRepo,
                        SubcategoryRepo subcategoryRepo,
                        ProductPropertyRepo productPropertyRepo,
                        PropertyRepo propertyRepo
    ) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.subcategoryRepo = subcategoryRepo;
        this.productPropertyRepo = productPropertyRepo;
        this.propertyRepo = propertyRepo;
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
    public Product addProduct(ProductRequest request) {
        Subcategory subcategory = subcategoryRepo.findById(request.getSubcategoryId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Данной подкатегории не существует"));

        ProductPropertyRequest productPropertyRequest = request.getProductPropertyRequest();
        List<Property> properties = propertyRepo.findAllById(productPropertyRequest.getPropertyIds());
        ProductProperty productProperty = ProductPropertyMapper.fromProductPropertyRequestToProductProperty(productPropertyRequest, properties);
        productPropertyRepo.save(productProperty);

        Product product = ProductMapper.fromProductRequestToProduct(request, productProperty, subcategory);
        productRepo.save(product);

        return product;
    }

    @Transactional
    public void updateProduct(ProductRequest request) {
        Product product = productRepo.findById(request.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

        Subcategory subcategory = subcategoryRepo.findById(request.getSubcategoryId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Данной подкатегории не существует"));

        ProductPropertyRequest productPropertyRequest = request.getProductPropertyRequest();
        List<Property> properties = propertyRepo.findAllById(productPropertyRequest.getPropertyIds());
        ProductProperty productProperty = ProductPropertyMapper.fromProductPropertyRequestToProductProperty(productPropertyRequest, properties);
        productPropertyRepo.save(productProperty);

        product.setPictureUrl(request.getPictureUrl());
        product.setSubcategory(subcategory);
        product.setProductProperty(productProperty);
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setAmount(request.getAmount());
        product.setPrice(request.getPrice());
        product.setDiscountPrice(request.getDiscountPrice());
        product.setDiscount(request.getDiscount());

        productRepo.save(product);
    }

    @Transactional
    public void updateProductProperty(Long id, PropertyRequest request) {
        productRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

        Property property = propertyRepo.findById(request.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Свойство не найдено"));

        property.setName(request.getName());
        property.setValue(request.getValue());

        propertyRepo.save(property);
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
