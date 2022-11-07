package com.shop.phoneshop.dto;

import com.shop.phoneshop.security.jwt.TokenResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponseDto {
    private final String tokenType = "Bearer";
    private String accessToken;
    private Instant accessExpiresIn;
    private String refreshToken;
    private Instant refreshExpiresIn;
    public JwtResponseDto(TokenResponse access, TokenResponse refresh) {
        accessToken = access.getToken();
        accessExpiresIn = access.getExpiresIn();
        refreshToken = refresh.getToken();
        refreshExpiresIn = refresh.getExpiresIn();
    }
}
