package com.shop.phoneshop.dto;

import com.shop.phoneshop.security.jwt.TokenResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponseDto {
    @ApiModelProperty(notes = "Тип токена", required = true)
    private final String tokenType = "Bearer";

    @ApiModelProperty(notes = "Токен доступа", required = true)
    private String accessToken;

    @ApiModelProperty(notes = "Когда токен истекает", required = true)
    private Instant accessExpiresIn;

    @ApiModelProperty(notes = "Refresh токен", required = true)
    private String refreshToken;

    @ApiModelProperty(notes = "Когда Refresh токен истекает", required = true)
    private Instant refreshExpiresIn;

    public JwtResponseDto(TokenResponse access, TokenResponse refresh) {
        accessToken = access.getToken();
        accessExpiresIn = access.getExpiresIn();
        refreshToken = refresh.getToken();
        refreshExpiresIn = refresh.getExpiresIn();
    }
}
