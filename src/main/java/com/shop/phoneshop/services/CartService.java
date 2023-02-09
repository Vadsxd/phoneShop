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
import com.shop.phoneshop.utils.ProductUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CartService {
    private final UserProductRepo userProductRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final CookieService cookieService;
    private final EmailService emailService;

    @Autowired
    public CartService(UserProductRepo userProductRepo,
                       UserRepo userRepo,
                       ProductRepo productRepo,
                       CookieService cookieService,
                       EmailService emailService
    ) {
        this.userProductRepo = userProductRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.cookieService = cookieService;
        this.emailService = emailService;
    }

    public CartDto getUserProducts(JwtAuthentication authentication) {
        if (authentication != null) {
            User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

            List<UserProduct> userProducts = userProductRepo.findAllByUser(user);
            List<UserProductDto> userProductDtos = UserProductMapper.fromUserProductsToDtos(userProducts, authentication);

            return CartMapper.fromUserProductDtosToCartDto(userProductDtos);
        } else {
            Cookie[] cookies = cookieService.getAllCookies();
            List<UserProductDto> userProductDtos = new ArrayList<>();

            if (cookies == null) {
                return CartMapper.fromUserProductDtosToCartDto(userProductDtos);
            }

            List<String> values = Arrays.stream(cookies)
                    .filter(c -> c.getName().contains("user_product_"))
                    .map(Cookie::getValue)
                    .toList();

            for (String value : values) {
                UserProductDto dto = new UserProductDto();
                JSONObject json = new JSONObject(URLDecoder.decode(value, StandardCharsets.UTF_8));
                dto.setPictureUrl((String) json.get("pictureUrl"));
                dto.setTitle((String) json.get("title"));
                dto.setPrice(((Number) json.get("price")).longValue());
                dto.setAmount(((Number) json.get("amount")).longValue());
                userProductDtos.add(dto);
            }

            return CartMapper.fromUserProductDtosToCartDto(userProductDtos);
        }
    }

    @Transactional
    public void reduceAmount(CartProductRequest request, JwtAuthentication authentication) {
        Product product = productRepo.findById(request.getProductId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

        if (authentication != null) {
            User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

            UserProduct userProduct = userProductRepo.findByProductAndUser(product, user).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар в корзине не найден"));

            Long amount = userProduct.getAmount();

            if (amount - 1 == 0) {
                userProductRepo.delete(userProduct);
            } else {
                userProduct.setAmount(amount - 1);
                userProductRepo.save(userProduct);
            }
        } else {
            Cookie cookie = cookieService.getCookie("user_product_" + product.getId());
            JSONObject json = new JSONObject(URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8));

            long cookieValue = ((Number) json.get("amount")).longValue();

            if (cookieValue - 1 == 0) {
                cookieService.deleteCookie("user_product_" + product.getId());
            } else {
                json.put("amount", cookieValue - 1);
                cookieService.setCookie("user_product_" + product.getId(), String.valueOf(json));
            }
        }
    }

    @Transactional
    public void addAmount(CartProductRequest request, JwtAuthentication authentication) {
        Product product = productRepo.findById(request.getProductId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

        if (authentication != null) {
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
        } else {
            Cookie cookie = cookieService.getCookie("user_product_" + product.getId());
            JSONObject json = new JSONObject(URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8));

            long cookieValue = ((Number) json.get("amount")).longValue();

            if (product.getAmount() < cookieValue + 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Такого количества товара нет на складе");
            } else {
                json.put("amount", cookieValue + 1);
                cookieService.setCookie("user_product_" + product.getId(), String.valueOf(json));
            }
        }
    }

    @Transactional
    public void addProduct(AddProductRequest request, JwtAuthentication authentication) {
        Product product = productRepo.findById(request.getProductId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

        if (product.getAmount() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Товара нет в наличии");
        }

        if (authentication != null) {
            User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

            if (userProductRepo.existsByProductTitleAndUser(product.getTitle(), user)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Товар уже в корзине");
            }

            UserProduct userProduct = new UserProduct();
            userProduct.setUser(user);
            userProduct.setProduct(product);
            userProduct.setAmount(1L);
            userProductRepo.save(userProduct);
        } else {
            JSONObject json = new JSONObject();
            json.put("pictureUrl", product.getPictureUrl());
            json.put("title", product.getTitle());
            json.put("price", product.getPrice());
            json.put("amount", 1L);
            cookieService.setCookie("user_product_" + product.getId(), String.valueOf(json));
        }
    }

    @Transactional
    public void deleteProduct(CartProductRequest request, JwtAuthentication authentication) {
        Product product = productRepo.findById(request.getProductId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

        if (authentication != null) {
            User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

            UserProduct userProduct = userProductRepo.findByProductAndUser(product, user).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар в корзине не найден"));

            userProductRepo.delete(userProduct);
        } else {
            cookieService.getCookie("user_product_" + product.getId());
            cookieService.deleteCookie("user_product_" + product.getId());
        }
    }

    //TODO J meter :)
    @Transactional
    public void buyProducts(JwtAuthentication authentication) {
        if (authentication != null) {
            User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

            List<UserProduct> userProducts = userProductRepo.findAllByUser(user);

            StringBuilder message = new StringBuilder();
            long fullPrice = 0L;

            for (UserProduct userProduct: userProducts) {
                Product product = userProduct.getProduct();
                Long productAmount = product.getAmount();
                if (productAmount <= 0) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Продукта " + product.getTitle()
                            + " нет в наличии");
                }
                if (productAmount < userProduct.getAmount()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "В начилии только " + productAmount);
                }
                Long productPrice = ProductUtil.getPrice(product, authentication);
                Long userProductPrice = userProduct.getAmount();
                fullPrice += productPrice * userProductPrice;
                product.setAmount(productAmount - userProduct.getAmount());
                message.append("Title: ")
                        .append(product.getTitle())
                        .append(". ")
                        .append("Amount: ")
                        .append(userProductPrice)
                        .append(". ")
                        .append("Price: ")
                        .append(productPrice)
                        .append("\n");
                productRepo.save(product);
            }
            message.append("Full price: ")
                    .append(fullPrice);

            emailService.sendSimpleEmail(user.getEmail(), "Shop Transaction", String.valueOf(message));
            userProductRepo.deleteAllByUser(user);
        } else {
            Cookie[] cookies = cookieService.getAllCookies();

            if (cookies == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Корзина пустая");
            }

            List<Cookie> sortCookies = Arrays.stream(cookies)
                    .filter(c -> c.getName().contains("user_product_"))
                    .toList();

            if (sortCookies.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Корзина пустая");
            }

            for (Cookie sortCookie : sortCookies) {
                Long productId = Long.valueOf(sortCookie.getName().replace("user_product_",""));
                Product product = productRepo.findById(productId).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
                Long productAmount = product.getAmount();
                if (productAmount <= 0) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Продукта " + product.getTitle()
                            + " нет в наличии");
                }
                JSONObject json = new JSONObject(URLDecoder.decode(sortCookie.getValue(), StandardCharsets.UTF_8));
                long cookieValue = ((Number) json.get("amount")).longValue();
                if (productAmount < cookieValue) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "В начилии только " + productAmount);
                }
                product.setAmount(productAmount - cookieValue);
                productRepo.save(product);
                cookieService.deleteCookie(sortCookie.getName());
            }
        }
    }
}
