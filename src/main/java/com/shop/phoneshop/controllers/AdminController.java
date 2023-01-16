package com.shop.phoneshop.controllers;

import com.shop.phoneshop.domain.Category;
import com.shop.phoneshop.requests.admin.CategoryRequest;
import com.shop.phoneshop.requests.admin.MoveSubcategoryRequest;
import com.shop.phoneshop.requests.admin.MoveSubcategoryToCategoryRequest;
import com.shop.phoneshop.services.AdminService;
import com.shop.phoneshop.utils.validation.Marker;
import io.swagger.annotations.Api;
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

    @Validated(Marker.onCreate.class)
    @PostMapping("/category")
    public ResponseEntity<Category> addCategory(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(adminService.addCategory(request));
    }

    @Validated(Marker.onUpdate.class)
    @PutMapping("/category")
    public void updateCategory(@Valid @RequestBody CategoryRequest request) {
        adminService.updateCategory(request);
    }

    @PutMapping("/moveSubcategoryToCategory")
    public void moveSubcategoryToCategory(@Valid @RequestBody MoveSubcategoryToCategoryRequest request) {
        adminService.moveSubcategoryToCategory(request);
    }

    @PutMapping("/moveSubcategory")
    public void moveSubcategory(@Valid @RequestBody MoveSubcategoryRequest request) {
        adminService.moveSubcategory(request);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);
    }

    @DeleteMapping("/deleteCategory/{id}")
    public void deleteCategory(@PathVariable Long id) {
        adminService.deleteCategory(id);
    }

    @DeleteMapping("/deleteSubcategory/{id}")
    public void deleteSubcategory(@PathVariable Long id) {
        adminService.deleteSubcategory(id);
    }
}
