package com.shop.phoneshop.services;

import com.shop.phoneshop.domain.Product;
import com.shop.phoneshop.domain.User;
import com.shop.phoneshop.domain.UserProduct;
import com.shop.phoneshop.dto.CartDto;
import com.shop.phoneshop.dto.UserProductDto;
import com.shop.phoneshop.mappers.CartMapper;
import com.shop.phoneshop.mappers.UserProductMapper;
import com.shop.phoneshop.repos.ProductRepo;
import com.shop.phoneshop.repos.UserProductRepo;
import com.shop.phoneshop.repos.UserRepo;
import com.shop.phoneshop.requests.AddProductRequest;
import com.shop.phoneshop.requests.CartProductRequest;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CartService {
    private final UserProductRepo userProductRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;

    public CartService(UserProductRepo userProductRepo, UserRepo userRepo, ProductRepo productRepo) {
        this.userProductRepo = userProductRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    public CartDto getUserProducts(JwtAuthentication authentication) {
        User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

        List<UserProduct> userProducts = userProductRepo.findAllByUser(user);
        List<UserProductDto> userProductDtos = UserProductMapper.fromUserProductsToDtos(userProducts);

        return CartMapper.fromUserProductDtosToCartDto(userProductDtos);
    }

    @Transactional
    public void reduceAmount(CartProductRequest request) {
        UserProduct userProduct = userProductRepo.findById(request.getUserProductId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар в корзине не найден"));

        Long amount = userProduct.getAmount();

        if (amount - 1 == 0) {
            userProductRepo.delete(userProduct);
        } else {
            userProduct.setAmount(amount - 1);
            userProductRepo.save(userProduct);
        }
    }

    @Transactional
    public void addAmount(CartProductRequest request) {
        UserProduct userProduct = userProductRepo.findById(request.getUserProductId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар в корзине не найден"));

        Product product = userProduct.getProduct();
        Long amount = userProduct.getAmount();

        if (product.getAmount() < amount + 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Такого количества товара нет на складе");
        } else {
            userProduct.setAmount(amount + 1);
            userProductRepo.save(userProduct);
        }
    }

    @Transactional
    public void addProduct(AddProductRequest request, JwtAuthentication authentication) {
        User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                new RuntimeException("Пользователь не найден"));

        Product product = productRepo.findById(request.getProductId()).orElseThrow(() ->
                new RuntimeException("Товар не найден"));

        UserProduct userProduct = new UserProduct();
        userProduct.setUser(user);
        userProduct.setProduct(product);
        userProduct.setAmount(1L);
        userProductRepo.save(userProduct);
    }

    @Transactional
    public void deleteProduct(CartProductRequest request, JwtAuthentication authentication) {

        UserProduct userProduct = userProductRepo.findById(request.getUserProductId()).orElseThrow(() ->
                new RuntimeException("Товар в корзине не найден"));

        userProductRepo.delete(userProduct);
    }
}
