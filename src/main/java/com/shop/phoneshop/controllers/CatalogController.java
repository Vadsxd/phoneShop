package com.shop.phoneshop.controllers;

import com.shop.phoneshop.dto.ProductDto;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import com.shop.phoneshop.services.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/products")
    ResponseEntity<List<ProductDto>> getAllProducts(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProducts(authentication));
    }

    @GetMapping("/smartphones")
    ResponseEntity<List<ProductDto>> getSmartphones(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromCategory("Smartphones",authentication));
    }

    @GetMapping("/audioTech")
    ResponseEntity<List<ProductDto>> getAudioTechnics(JwtAuthentication authentication) {
        return ResponseEntity.ok(catalogService.getAllProductsFromCategory("AudioTechnics", authentication));
    }
}
