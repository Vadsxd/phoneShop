package com.shop.phoneshop.controllers;

import com.shop.phoneshop.dto.ProductDto;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import com.shop.phoneshop.services.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {
    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/AllProducts")
    ResponseEntity<List<ProductDto>> getAllProducts(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProducts(authentication));
    }

    @GetMapping("/product/{id}")
    ResponseEntity<ProductDto> getProduct(@PathVariable Long id, JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getProduct(id, authentication));
    }

    @GetMapping("/smartphones")
    ResponseEntity<List<ProductDto>> getSmartphones(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromCategory("Smartphones", authentication));
    }

    @GetMapping("/audioTechs")
    ResponseEntity<List<ProductDto>> getAudioTechnics(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromCategory("AudioTechnics", authentication));
    }

    @GetMapping("/audioTechs/portableSpeakers")
    ResponseEntity<List<ProductDto>> getPortableSpeakers(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromSubcategory("PortableSpeakers", authentication));
    }

    @GetMapping("/audioTechs/headphones")
    ResponseEntity<List<ProductDto>> getHeadphones(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromSubcategory("Headphones", authentication));
    }

    @GetMapping("/smartphones/apple")
    ResponseEntity<List<ProductDto>> getAppleSmartphones(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromSubcategory("Apple", authentication));
    }

    @GetMapping("/smartphones/onePlus")
    ResponseEntity<List<ProductDto>> getOnePlusSmartphones(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromSubcategory("OnePlus", authentication));
    }

    @GetMapping("/smartphones/extraProducts")
    ResponseEntity<List<ProductDto>> getSmartphonesExtraProducts(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getSmartphonesExtraProducts(authentication));
    }
}
