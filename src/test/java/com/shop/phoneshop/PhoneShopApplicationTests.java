package com.shop.phoneshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.phoneshop.domain.User;
import com.shop.phoneshop.dto.JwtResponseDto;
import com.shop.phoneshop.repos.ProductRepo;
import com.shop.phoneshop.repos.UserProductRepo;
import com.shop.phoneshop.repos.UserRepo;
import com.shop.phoneshop.requests.AddProductRequest;
import com.shop.phoneshop.requests.CartProductRequest;
import com.shop.phoneshop.requests.auth.AuthRequest;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import com.shop.phoneshop.security.jwt.JwtProvider;
import com.shop.phoneshop.security.jwt.JwtUtils;
import com.shop.phoneshop.services.AuthService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.LongStream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PhoneShopApplicationTests {
    private final String userEmail = "vadim.sannikov.2018@mail.ru";
    private final MockMvc mockMvc;
    private final ProductRepo productRepo;
    private final UserProductRepo userProductRepo;
    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final UserRepo userRepo;

    @Autowired
    public PhoneShopApplicationTests(
            MockMvc mockMvc,
            ProductRepo productRepo,
            UserProductRepo userProductRepo,
            AuthService authService,
            JwtProvider jwtProvider,
            UserRepo userRepo
    ) {
        this.mockMvc = mockMvc;
        this.productRepo = productRepo;
        this.userProductRepo = userProductRepo;
        this.authService = authService;
        this.jwtProvider = jwtProvider;
        this.userRepo = userRepo;
    }

    private JwtAuthentication authUser() {
        AuthRequest request = new AuthRequest();
        request.setUserEmail(userEmail);
        request.setUserPassword("123");

        JwtResponseDto jwtResponseDto = authService.authenticateUser(request);
        Claims claims = jwtProvider.getAccessClaims(jwtResponseDto.getAccessToken());

        return JwtUtils.getAuthentication(claims);
    }

    @Test
    void checkEmptyCart() throws Exception {
        mockMvc
                .perform(get("/api/cart").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(0));
    }

    @Test
    void notExistingProduct() throws Exception {
        mockMvc
                .perform(get("/api/catalog/product/{id}", 100).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    void addProduct() throws Exception {
        AddProductRequest request = new AddProductRequest();
        request.setProductId(1L);

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(request);

        mockMvc
                .perform(post("/api/addProduct").contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("user_product_1"))
                .andExpect(cookie().value("user_product_1",
                        "%7B%22amount%22%3A1%2C%22price%22%3A90000%2C%22pictureUrl%22%3A%22some+url%22%2C%22title%22%3A%22OpenPlus7Pro%22%7D"));
    }

    @Test
    void checkCartIncrease() throws Exception {
        User user = userRepo.findByEmail(userEmail);
        CartProductRequest request = new CartProductRequest();
        request.setProductId(1L);

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(request);

        long cartCount = userProductRepo.findAllByUser(user).stream()
                .flatMapToLong(a -> LongStream.of(a.getAmount()))
                .sum();

        mockMvc
                .perform(put("/api/cart/addAmount").contentType(MediaType.APPLICATION_JSON)
                        .content(bytes)
                        .with(authentication(authUser())))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/cart").contentType(MediaType.APPLICATION_JSON)
                .with(authentication(authUser())))
                .andExpect(jsonPath("$.count").value(cartCount + 1));
    }

    @Test
    void checkAllProductsWithCart() throws Exception {
        int productsCount = productRepo.findAll().size();
        User user = userRepo.findByEmail(userEmail);
        long cartCount = userProductRepo.findAllByUser(user).stream()
                .flatMapToLong(a -> LongStream.of(a.getAmount()))
                .sum();

        mockMvc
                .perform(get("/api/catalog").contentType(MediaType.APPLICATION_JSON)
                        .with(authentication(authUser())))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categoryCount").value(productsCount))
                .andExpect(jsonPath("$.cartCount").value(cartCount));
    }

    @Test
    void FailedTransaction() throws Exception {
        mockMvc
                .perform(post("/api/cart/transaction").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

}
