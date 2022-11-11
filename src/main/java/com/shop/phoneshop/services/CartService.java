package com.shop.phoneshop.services;

import com.shop.phoneshop.domain.User;
import com.shop.phoneshop.domain.UserProduct;
import com.shop.phoneshop.dto.CartDto;
import com.shop.phoneshop.dto.UserProductDto;
import com.shop.phoneshop.mappers.CartMapper;
import com.shop.phoneshop.mappers.UserProductMapper;
import com.shop.phoneshop.repos.UserProductRepo;
import com.shop.phoneshop.repos.UserRepo;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final UserProductRepo userProductRepo;
    private final UserRepo userRepo;

    public CartService(UserProductRepo userProductRepo, UserRepo userRepo) {
        this.userProductRepo = userProductRepo;
        this.userRepo = userRepo;
    }

    public CartDto getUserProducts(JwtAuthentication authentication) {
        User user = userRepo.findById(authentication.getUserId()).orElseThrow(() ->
                new RuntimeException("Пользователь не найден"));

        List<UserProduct> userProducts = userProductRepo.findAllByUser(user);
        List<UserProductDto> userProductDtos = UserProductMapper.fromUserProductsToDtos(userProducts);

        return CartMapper.fromUserProductDtosToCartDto(userProductDtos);
    }
}
