package com.shop.phoneshop.controllers;

import com.shop.phoneshop.dto.CartDto;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import com.shop.phoneshop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/userProducts")
    ResponseEntity<CartDto> getCartProducts(JwtAuthentication authentication) {
        return ResponseEntity.ok(cartService.getUserProducts(authentication));
    }
}
