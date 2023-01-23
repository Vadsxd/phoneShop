package com.shop.phoneshop.controllers;

import com.shop.phoneshop.dto.CatalogDto;
import com.shop.phoneshop.dto.ProductDto;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import com.shop.phoneshop.services.CatalogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("Получить все товары из категории Смартфоны")
    @GetMapping("/catalog/smartphones")
    @ApiResponses(@ApiResponse(code = 404, message = "Товаров из данной категории нет"))
    public ResponseEntity<CatalogDto> getSmartphones(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromCategory("Smartphones", authentication));
    }

    @ApiOperation("Получить все товары из категории Аудиотехника")
    @GetMapping("/catalog/audioTechs")
    @ApiResponses(@ApiResponse(code = 404, message = "Товаров из данной категории нет"))
    public ResponseEntity<CatalogDto> getAudioTechnics(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromCategory("AudioTechnics", authentication));
    }

    @ApiOperation("Получить все товары из подкатегории Портативные колонки")
    @GetMapping("/catalog/audioTechs/portableSpeakers")
    @ApiResponses(@ApiResponse(code = 404, message = "Товаров из данной подкатегории нет"))
    public ResponseEntity<CatalogDto> getPortableSpeakers(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromSubcategory("PortableSpeakers", authentication));
    }

    @ApiOperation("Получить все товары из подкатегории Наушники")
    @GetMapping("/catalog/audioTechs/headphones")
    @ApiResponses(@ApiResponse(code = 404, message = "Товаров из данной подкатегории нет"))
    public ResponseEntity<CatalogDto> getHeadphones(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromSubcategory("Headphones", authentication));
    }

    @ApiOperation("Получить все товары из подкатегории Apple")
    @GetMapping("/catalog/smartphones/apple")
    @ApiResponses(@ApiResponse(code = 404, message = "Товаров из данной подкатегории нет"))
    public ResponseEntity<CatalogDto> getAppleSmartphones(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromSubcategory("Apple", authentication));
    }

    @ApiOperation("Получить все товары из подкатегории OnePlus")
    @GetMapping("/catalog/smartphones/onePlus")
    @ApiResponses(@ApiResponse(code = 404, message = "Товаров из данной подкатегории нет"))
    public ResponseEntity<CatalogDto> getOnePlusSmartphones(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromSubcategory("OnePlus", authentication));
    }

    @ApiOperation("Получить все товары из подкатегории Дополнительные товары для смартфонов")
    @GetMapping("/catalog/smartphones/extraProducts")
    @ApiResponses(@ApiResponse(code = 404, message = "Товаров из данной подкатегории нет"))
    public ResponseEntity<CatalogDto> getSmartphonesExtraProducts(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getSmartphonesExtraProducts(authentication));
    }
}
