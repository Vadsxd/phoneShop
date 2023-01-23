package com.shop.phoneshop.controllers;

import com.shop.phoneshop.domain.User;
import com.shop.phoneshop.dto.CatalogDto;
import com.shop.phoneshop.dto.ProductDto;
import com.shop.phoneshop.requests.DeleteFeedbackRequest;
import com.shop.phoneshop.requests.FeedbackRequest;
import com.shop.phoneshop.requests.auth.RegisterRequest;
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
    ResponseEntity<CatalogDto> getAllProducts(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProducts(authentication));
    }

    @ApiOperation("Получить товар по id")
    @GetMapping("/catalog/product/{id}")
    @ApiResponses(@ApiResponse(code = 404, message = "Товар не существует"))
    ResponseEntity<ProductDto> getProduct(@PathVariable Long id, JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getProduct(id, authentication));
    }

    @ApiOperation("Получить товар по id, отзывы упорядочены по возрастанию оценки")
    @GetMapping("/catalog/product/{id}/sortedAscending")
    @ApiResponses(@ApiResponse(code = 404, message = "Товар не существует"))
    ResponseEntity<ProductDto> getProductFeedbacksSortedAscending(@PathVariable Long id, JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getProductFeedbacksSortedAscending(id, authentication));
    }

    @ApiOperation("Получить товар по id, отзывы упорядочены по убыванию оценки")
    @GetMapping("/catalog/product/{id}/sortedDescending")
    @ApiResponses(@ApiResponse(code = 404, message = "Товар не существует"))
    ResponseEntity<ProductDto> getProductFeedbacksSortedDescending(@PathVariable Long id, JwtAuthentication authentication) {
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

    @ApiOperation("Удаление отзыва полностью")
    @ApiResponses(@ApiResponse(code = 400, message = "Товар не существует"))
    @DeleteMapping("/catalog/product/{id}/deleteFeedback")
    public ResponseEntity<ProductDto> deleteFeedback(@Valid @RequestBody DeleteFeedbackRequest request,
                                                     JwtAuthentication authentication,
                                                     @PathVariable Long id) {
        return ResponseEntity.ok(catalogService.deleteFeedback(request, authentication, id));
    }

    @ApiOperation("Удаление фотографий из отзыва")
    @ApiResponses(@ApiResponse(code = 400, message = "Товар не существует"))
    @DeleteMapping("/catalog/product/{id}/deletePhotosFeedback")
    public ResponseEntity<ProductDto> deletePhotosFeedback(@Valid @RequestBody DeleteFeedbackRequest request,
                                                     JwtAuthentication authentication,
                                                     @PathVariable Long id) {
        return ResponseEntity.ok(catalogService.deletePhotosFeedback(request, authentication, id));
    }

    @ApiOperation("Удаление фотографий из отзыва")
    @ApiResponses(@ApiResponse(code = 400, message = "Товар не существует"))
    @DeleteMapping("/catalog/product/{id}/deleteCommentFeedback")
    public ResponseEntity<ProductDto> deleteCommentFeedback(@Valid @RequestBody DeleteFeedbackRequest request,
                                                           JwtAuthentication authentication,
                                                           @PathVariable Long id) {
        return ResponseEntity.ok(catalogService.deleteCommentFeedback(request, authentication, id));
    }

    @ApiOperation("Получить все товары из категории Смартфоны")
    @GetMapping("/catalog/smartphones")
    @ApiResponses(@ApiResponse(code = 404, message = "Товаров из данной категории нет"))
    ResponseEntity<CatalogDto> getSmartphones(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromCategory("Smartphones", authentication));
    }

    @ApiOperation("Получить все товары из категории Аудиотехника")
    @GetMapping("/catalog/audioTechs")
    @ApiResponses(@ApiResponse(code = 404, message = "Товаров из данной категории нет"))
    ResponseEntity<CatalogDto> getAudioTechnics(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromCategory("AudioTechnics", authentication));
    }

    @ApiOperation("Получить все товары из подкатегории Портативные колонки")
    @GetMapping("/catalog/audioTechs/portableSpeakers")
    @ApiResponses(@ApiResponse(code = 404, message = "Товаров из данной подкатегории нет"))
    ResponseEntity<CatalogDto> getPortableSpeakers(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromSubcategory("PortableSpeakers", authentication));
    }

    @ApiOperation("Получить все товары из подкатегории Наушники")
    @GetMapping("/catalog/audioTechs/headphones")
    @ApiResponses(@ApiResponse(code = 404, message = "Товаров из данной подкатегории нет"))
    ResponseEntity<CatalogDto> getHeadphones(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromSubcategory("Headphones", authentication));
    }

    @ApiOperation("Получить все товары из подкатегории Apple")
    @GetMapping("/catalog/smartphones/apple")
    @ApiResponses(@ApiResponse(code = 404, message = "Товаров из данной подкатегории нет"))
    ResponseEntity<CatalogDto> getAppleSmartphones(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromSubcategory("Apple", authentication));
    }

    @ApiOperation("Получить все товары из подкатегории OnePlus")
    @GetMapping("/catalog/smartphones/onePlus")
    @ApiResponses(@ApiResponse(code = 404, message = "Товаров из данной подкатегории нет"))
    ResponseEntity<CatalogDto> getOnePlusSmartphones(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromSubcategory("OnePlus", authentication));
    }

    @ApiOperation("Получить все товары из подкатегории Дополнительные товары для смартфонов")
    @GetMapping("/catalog/smartphones/extraProducts")
    @ApiResponses(@ApiResponse(code = 404, message = "Товаров из данной подкатегории нет"))
    ResponseEntity<CatalogDto> getSmartphonesExtraProducts(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getSmartphonesExtraProducts(authentication));
    }
}
