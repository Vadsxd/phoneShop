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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final UserProductRepo userProductRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final CookieService cookieService;

    @Autowired
    public CartService(UserProductRepo userProductRepo,
                       UserRepo userRepo,
                       ProductRepo productRepo,
                       CookieService cookieService
    ) {
        this.userProductRepo = userProductRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.cookieService = cookieService;
    }

    public CartDto getUserProducts(JwtAuthentication authentication) {
        if (authentication != null) {
            User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

            List<UserProduct> userProducts = userProductRepo.findAllByUser(user);
            List<UserProductDto> userProductDtos = UserProductMapper.fromUserProductsToDtos(userProducts);

            return CartMapper.fromUserProductDtosToCartDto(userProductDtos);
        } else {
            Cookie[] cookies = cookieService.getCookie();
            List<UserProductDto> userProductDtos = new ArrayList<>();

            if (cookies == null) {
                return CartMapper.fromUserProductDtosToCartDto(userProductDtos);
            }

            List<String> values = Arrays.stream(cookies)
                    .map(Cookie::getValue).toList();
            for (int i = 0; i < values.size(); i += 4) {
                UserProductDto dto = new UserProductDto();
                dto.setPictureUrl(URLDecoder.decode(values.get(i), StandardCharsets.UTF_8));
                dto.setTitle(URLDecoder.decode(values.get(i + 1), StandardCharsets.UTF_8));
                dto.setPrice(Long.parseLong(URLDecoder.decode(values.get(i + 2), StandardCharsets.UTF_8)));
                dto.setAmount(Long.parseLong(URLDecoder.decode(values.get(i + 3), StandardCharsets.UTF_8)));
                userProductDtos.add(dto);
            }

            return CartMapper.fromUserProductDtosToCartDto(userProductDtos);
        }
    }

    @Transactional
    public void reduceAmount(CartProductRequest request, JwtAuthentication authentication) {
        User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

        Product product = productRepo.findById(request.getProductId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

        UserProduct userProduct = userProductRepo.findByProductAndUser(product, user).orElseThrow(() ->
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
    public void addAmount(CartProductRequest request, JwtAuthentication authentication) {
        Product product = productRepo.findById(request.getProductId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

        User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

        UserProduct userProduct = userProductRepo.findByProductAndUser(product, user).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар в корзине не найден"));

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
        Product product = productRepo.findById(request.getProductId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

        if (authentication != null) {
            User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

            if (userProductRepo.existsByProductTitle(product.getTitle())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Товар уже в корзине");
            }

            UserProduct userProduct = new UserProduct();
            userProduct.setUser(user);
            userProduct.setProduct(product);
            userProduct.setAmount(1L);
            userProductRepo.save(userProduct);
        } else {
            Long productId = product.getId();
            cookieService.setCookie("pictureUrl" + productId, product.getPictureUrl());
            cookieService.setCookie("title" + productId, product.getTitle());
            cookieService.setCookie("price" + productId, String.valueOf(product.getPrice()));
            cookieService.setCookie("amount" + productId, String.valueOf(1L));
        }
    }

    @Transactional
    public void deleteProduct(CartProductRequest request, JwtAuthentication authentication) {
        if (authentication != null) {
            Product product = productRepo.findById(request.getProductId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

            User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

            UserProduct userProduct = userProductRepo.findByProductAndUser(product, user).orElseThrow(() ->
                    new RuntimeException("Товар в корзине не найден"));

            userProductRepo.delete(userProduct);
        } else {
            Long productId = request.getProductId();
            cookieService.deleteCookie("pictureUrl" + productId);
            cookieService.deleteCookie("title" + productId);
            cookieService.deleteCookie("price" + productId);
            cookieService.deleteCookie("amount" + productId);
        }
    }
}
