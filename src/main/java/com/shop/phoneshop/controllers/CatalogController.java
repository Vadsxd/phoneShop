package com.shop.phoneshop.controllers;

import com.shop.phoneshop.dto.CatalogDto;
import com.shop.phoneshop.dto.ProductDto;
import com.shop.phoneshop.requests.FeedbackRequest;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import com.shop.phoneshop.services.CatalogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Каталог")
@RestController
@RequestMapping("/api")
public class CatalogController {
    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @ApiOperation("Получить все товары")
    @GetMapping("/catalog")
    public ResponseEntity<CatalogDto> getAllProducts(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProducts(authentication));
    }

    @ApiOperation("Получить товар по id")
    @GetMapping("/catalog/product/{id}")
    @ApiResponses(@ApiResponse(code = 404, message = "Товар не существует"))
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id, JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getProduct(id, authentication));
    }

    @ApiOperation("Получить товар по id, отзывы упорядочены по возрастанию оценки")
    @GetMapping("/catalog/product/{id}/sortedAscending")
    @ApiResponses(@ApiResponse(code = 404, message = "Товар не существует"))
    public ResponseEntity<ProductDto> getProductFeedbacksSortedAscending(@PathVariable Long id, JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getProductFeedbacksSortedAscending(id, authentication));
    }

    @ApiOperation("Получить товар по id, отзывы упорядочены по убыванию оценки")
    @GetMapping("/catalog/product/{id}/sortedDescending")
    @ApiResponses(@ApiResponse(code = 404, message = "Товар не существует"))
    public ResponseEntity<ProductDto> getProductFeedbacksSortedDescending(@PathVariable Long id, JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getProductFeedbacksSortedDescending(id, authentication));
    }

    @ApiOperation("Добавление отзыва")
    @ApiResponses(@ApiResponse(code = 400, message = "Некорректные данные"))
    @PostMapping("/catalog/product/{id}/addFeedback")
    public ResponseEntity<ProductDto> addFeedback(@Valid @RequestBody FeedbackRequest request,
                                                  JwtAuthentication authentication,
                                                  @PathVariable Long id) {
        return ResponseEntity.ok(catalogService.addFeedback(request, authentication, id));
    }

    @ApiOperation("Получить все товары из категории")
    @GetMapping("/catalog/category/{id}")
    @ApiResponses(@ApiResponse(code = 404, message = "Данной категории не существует"))
    public ResponseEntity<CatalogDto> getSmartphones(JwtAuthentication authentication,
                                                     @PathVariable Long id) {
        return ResponseEntity.ok(catalogService.getAllProductsFromCategory(id, authentication));
    }

    @ApiOperation("Получить все товары из подкатегории")
    @GetMapping("/catalog/subcategory/{id}")
    @ApiResponses(@ApiResponse(code = 404, message = "Данной подкатегории не существует"))
    public ResponseEntity<CatalogDto> getPortableSpeakers(JwtAuthentication authentication,
                                                          @PathVariable Long id) {
        return ResponseEntity.ok(catalogService.getAllProductsFromSubcategory(id, authentication));
    }
}
