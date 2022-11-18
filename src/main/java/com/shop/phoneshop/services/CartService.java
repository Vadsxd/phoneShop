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
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    public CartDto getUserProducts(JwtAuthentication authentication, HttpServletRequest httpServletRequest) {
        if (authentication != null) {
            User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

            List<UserProduct> userProducts = userProductRepo.findAllByUser(user);
            List<UserProductDto> userProductDtos = UserProductMapper.fromUserProductsToDtos(userProducts);

            return CartMapper.fromUserProductDtosToCartDto(userProductDtos);
        } else {
            Cookie[] cookies = cookieService.getCookie(httpServletRequest);
        }
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
        if (authentication != null) {
            User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

            Product product = productRepo.findById(request.getProductId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

            if (productRepo.existsByTitle(product.getTitle())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Товар уже в корзине");
            }

            UserProduct userProduct = new UserProduct();
            userProduct.setUser(user);
            userProduct.setProduct(product);
            userProduct.setAmount(1L);
            userProductRepo.save(userProduct);
        } else {
            //TODO сделать куки
            cookieService.setCookie("product", request.getProductId().toString());
        }
    }
}
