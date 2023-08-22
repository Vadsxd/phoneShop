package com.shop.phoneshop.controllers;

import com.shop.phoneshop.domain.Category;
import com.shop.phoneshop.domain.Property;
import com.shop.phoneshop.domain.Subcategory;
import com.shop.phoneshop.requests.DeleteFeedbackRequest;
import com.shop.phoneshop.requests.admin.*;
import com.shop.phoneshop.responses.AddProductResponse;
import com.shop.phoneshop.services.AdminService;
import com.shop.phoneshop.utils.validation.Marker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Администратор")
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @ApiOperation("Добавить категорию")
    @Validated(Marker.onCreate.class)
    @PostMapping("/category")
    public ResponseEntity<Category> addCategory(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(adminService.addCategory(request));
    }

    @ApiOperation("Редактировать категорию")
    @ApiResponses(@ApiResponse(code = 404, message = "Категории не существует"))
    @Validated(Marker.onUpdate.class)
    @PutMapping("/category")
    public void updateCategory(@Valid @RequestBody CategoryRequest request) {
        adminService.updateCategory(request);
    }

    @ApiOperation("Добавить подкатегорию")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = """
                    Категории не существует
                    Родительской подкатегории не существует
                    """)
    })
    @Validated(Marker.onCreate.class)
    @PostMapping("/subcategory")
    public ResponseEntity<Subcategory> addSubcategory(@Valid @RequestBody SubcategoryRequest request) {
        return ResponseEntity.ok(adminService.addSubcategory(request));
    }

    @ApiOperation("Редактировать подкатегорию")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = """
                    Категории не существует
                    Подкатегории не существует
                    Родительской подкатегории не существует
                    """)
    })
    @Validated(Marker.onUpdate.class)
    @PutMapping("/subcategory")
    public void updateSubcategory(@Valid @RequestBody SubcategoryRequest request) {
        adminService.updateSubcategory(request);
    }

    @ApiOperation("Перемещение подкатегорию в другую категорию")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = """
                    Категории не существует
                    Подкатегории не существует
                    """)
    })
    @PutMapping("/moveSubcategoryToCategory")
    public void moveSubcategoryToCategory(@Valid @RequestBody MoveSubcategoryToCategoryRequest request) {
        adminService.moveSubcategoryToCategory(request);
    }

    @ApiOperation("Перемещение подкатегории внутри категории")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = """
                    Подкатегории не существует
                    Родительской подкатегории не существует
                    """)
    })
    @PutMapping("/moveSubcategory")
    public void moveSubcategory(@Valid @RequestBody MoveSubcategoryRequest request) {
        adminService.moveSubcategory(request);
    }

    @ApiOperation("Добавить товар")
    @ApiResponses(@ApiResponse(code = 404, message = "Подкатегории не существует"))
    @Validated(Marker.onCreate.class)
    @PostMapping("/product")
    public ResponseEntity<AddProductResponse> addProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(adminService.addProduct(request));
    }

    @ApiOperation("Отредактировать существующий товар")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = """
                    Подкатегории не существует
                    Товара не существует
                    """)
    })
    @Validated(Marker.onUpdate.class)
    @PutMapping("/product")
    public void updateProduct(@Valid @RequestBody ProductRequest request) {
        adminService.updateProduct(request);
    }

    @ApiOperation("Добавить характеристику")
    @ApiResponses(@ApiResponse(code = 404, message = "Неверный id набора свойств"))
    @Validated(Marker.onCreate.class)
    @PostMapping("/property")
    public ResponseEntity<Property> addProperty(@Valid @RequestBody AddPropertyRequest request) {
        return ResponseEntity.ok(adminService.addProperty(request));
    }

    @ApiOperation("Редактировать характеристику")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = """
                    Неверный id набора свойств
                    Свойства не существует
                    """)
    })
    @Validated(Marker.onUpdate.class)
    @PutMapping("/property")
    public void updateProperty(@Valid @RequestBody AddPropertyRequest request) {
        adminService.updateProperty(request);
    }

    @ApiOperation("Редактировать характеристику товара")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = """
                    Свойства не существует
                    Товара не существует
                    """)
    })
    @PutMapping("/product/{id}/updateProperty")
    public void updateProductProperty(@PathVariable Long id, @Valid @RequestBody PropertyRequest request) {
        adminService.updateProductProperty(id, request);
    }

    @ApiOperation("Удалить товар")
    @ApiResponses(@ApiResponse(code = 404, message = "Товара не существует"))
    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);
    }

    @ApiOperation("Удалить категорию")
    @ApiResponses(@ApiResponse(code = 404, message = "Категории не существует"))
    @DeleteMapping("/deleteCategory/{id}")
    public void deleteCategory(@PathVariable Long id) {
        adminService.deleteCategory(id);
    }

    @ApiOperation("Удалить подкатегорию")
    @ApiResponses(@ApiResponse(code = 404, message = "Подкатегории не существует"))
    @DeleteMapping("/deleteSubcategory/{id}")
    public void deleteSubcategory(@PathVariable Long id) {
        adminService.deleteSubcategory(id);
    }

    @ApiOperation("Удаление отзыва полностью")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Товар не существует"),
            @ApiResponse(code = 404, message = """
                    Отзыва не существует
                    Товара не существует
                    """)
    })
    @DeleteMapping("/catalog/product/{id}/deleteFeedback")
    public void deleteFeedback(@Valid @RequestBody DeleteFeedbackRequest request, @PathVariable Long id) {
        adminService.deleteFeedback(request, id);
    }

    @ApiOperation("Удаление фотографий из отзыва")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Товар не существует"),
            @ApiResponse(code = 404, message = """
                    Отзыва не существует
                    Товара не существует
                    """)
    })
    @DeleteMapping("/catalog/product/{id}/deletePhotosFeedback")
    public void deletePhotosFeedback(@Valid @RequestBody DeleteFeedbackRequest request, @PathVariable Long id) {
        adminService.deletePhotosFeedback(request, id);
    }

    @ApiOperation("Удаление комментария из отзыва")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Товар не существует"),
            @ApiResponse(code = 404, message = """
                    Отзыва не существует
                    Товара не существует
                    """)
    })
    @PutMapping("/catalog/product/{id}/deleteCommentFeedback")
    public void deleteCommentFeedback(@Valid @RequestBody DeleteFeedbackRequest request, @PathVariable Long id) {
        adminService.deleteCommentFeedback(request, id);
    }
}
