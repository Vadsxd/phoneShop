package com.shop.phoneshop.controllers;

import com.shop.phoneshop.domain.User;
import com.shop.phoneshop.dto.JwtResponseDto;
import com.shop.phoneshop.requests.auth.AuthRequest;
import com.shop.phoneshop.requests.auth.RefreshTokenRequest;
import com.shop.phoneshop.requests.auth.RegisterRequest;
import com.shop.phoneshop.security.jwt.JwtAuthentication;
import com.shop.phoneshop.services.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "Авторизация и аутентификация")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation("Регистрация пользователя")
    @ApiResponses(@ApiResponse(code = 400, message = "Некорректные данные"))
    @PostMapping("/register")
    public ResponseEntity<User> login(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @ApiOperation("Получить пару JWT токенов по логину/паролю")
    @ApiResponses(@ApiResponse(code = 400, message = "Некорректные данные"))
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticateUser(request));
    }

    @ApiOperation("Получить новую пару JWT токенов по refresh-токену")
    @ApiResponses(@ApiResponse(code = 400, message = "Некорректный refresh-токен"))
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDto> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshTokens(request));
    }

    @ApiOperation("Аннулировать все выданные пользователю refresh-токены")
    @ApiResponses(@ApiResponse(code = 404, message = "Пользователь не найден"))
    @PostMapping("/logout")
    public void logout(JwtAuthentication authentication) {
        authService.deleteAllById(authentication.getUserId());
    }
}
