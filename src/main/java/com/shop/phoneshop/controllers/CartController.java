package com.shop.phoneshop.controllers;

import com.shop.phoneshop.dto.CartDto;
import com.shop.phoneshop.requests.AddProductRequest;
import com.shop.phoneshop.requests.CartProductRequest;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import com.shop.phoneshop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    ResponseEntity<CartDto> getCartProducts(JwtAuthentication authentication) {
        return ResponseEntity.ok(cartService.getUserProducts(authentication));
    }

    @PostMapping("/addProduct")
    public void addProduct(
            @Valid @RequestBody AddProductRequest request,
            JwtAuthentication authentication
            ) {
        cartService.addProduct(request, authentication);
    }

    @PostMapping("/cart/addAmount")
    public void addAmount(@Valid @RequestBody CartProductRequest request) {
        cartService.addAmount(request);
    }

    @PostMapping("/cart/reduceAmount")
    public void reduceAmount(@Valid @RequestBody CartProductRequest request) {
        cartService.reduceAmount(request);
    }

    @DeleteMapping("/cart/deleteProduct")
    public void deleteProduct(
            @Valid @RequestBody CartProductRequest request,
            JwtAuthentication authentication
    ) {
        cartService.deleteProduct(request, authentication);
    }
}
