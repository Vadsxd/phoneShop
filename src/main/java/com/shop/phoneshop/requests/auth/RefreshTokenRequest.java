package com.shop.phoneshop.requests.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class RefreshTokenRequest {
    @ApiModelProperty(notes = "Refresh токен", required = true)
    @NotBlank
    private String refreshToken;
}
