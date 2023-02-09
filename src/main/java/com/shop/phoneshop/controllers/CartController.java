package com.shop.phoneshop.controllers;

import com.shop.phoneshop.dto.CartDto;
import com.shop.phoneshop.requests.AddProductRequest;
import com.shop.phoneshop.requests.CartProductRequest;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import com.shop.phoneshop.services.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Корзина")
@Slf4j
@RestController
@RequestMapping("/api")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @ApiOperation("Получить товары, добавленные в корзину")
    @GetMapping("/cart")
    public ResponseEntity<CartDto> getCartProducts(JwtAuthentication authentication) {
        return ResponseEntity.ok(cartService.getUserProducts(authentication));
    }

    @ApiOperation("Добавить товар в корзину")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Товар не существует"),
            @ApiResponse(code = 400, message = "Товара нет в наличии")
    })
    @PostMapping("/addProduct")
    public void addProduct(
            @Valid @RequestBody AddProductRequest request,
            JwtAuthentication authentication
            ) {
        cartService.addProduct(request, authentication);
    }

    @ApiOperation("Увеличить количество товара в корзине")
    @PutMapping("/cart/addAmount")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Товар не существует"),
            @ApiResponse(code = 400, message = """
                    Превышен лимит товара
                    Товар не добавлен в корзину
                    """)
    })
    public void addAmount(@Valid @RequestBody CartProductRequest request, JwtAuthentication authentication) {
        cartService.addAmount(request, authentication);
    }

    @ApiOperation("Уменьшить количество товара в корзине")
    @PostMapping("/cart/reduceAmount")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Товар не существует"),
            @ApiResponse(code = 400, message = "Товар не добавлен в корзину")
    })
    public void reduceAmount(@Valid @RequestBody CartProductRequest request, JwtAuthentication authentication) {
        cartService.reduceAmount(request, authentication);
    }

    @ApiOperation("Совершить покупку")
    @PostMapping("/cart/transaction")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Ошибка на сервере почты"),
            @ApiResponse(code = 404, message = "Товар не существует"),
            @ApiResponse(code = 400, message = "Корзина пустая")
    })
    public void buyProducts(JwtAuthentication authentication) {
        cartService.buyProducts(authentication);
    }

    @ApiOperation("Удалить продукт из корзины")
    @DeleteMapping("/cart/deleteProduct")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Товар не существует"),
            @ApiResponse(code = 400, message = "Товар не добавлен в корзину")
    })
    public void deleteProduct(
            @Valid @RequestBody CartProductRequest request,
            JwtAuthentication authentication
    ) {
        cartService.deleteProduct(request, authentication);
    }
}
