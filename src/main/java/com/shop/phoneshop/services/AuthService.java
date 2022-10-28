package com.shop.phoneshop.services;

import com.shop.phoneshop.domain.User;
import com.shop.phoneshop.repos.RefreshTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthService {
    private final UserService userService;
    private final RefreshTokenRepo refreshTokenRepo;

    @Autowired
    public AuthService(UserService userService,
                       RefreshTokenRepo refreshTokenRepo
    ) {
        this.userService = userService;
        this.refreshTokenRepo = refreshTokenRepo;
    }

    @Transactional
    public void deleteAllById(Long id) {
        User user = userService.getUserById(id);
        refreshTokenRepo.deleteAllByUser(user);
    }
}
