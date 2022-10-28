package com.shop.phoneshop.requests.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class AuthRequest {
    @NotBlank
    private String authorizationCode;

    @NotBlank
    private String redirectUrl;
}
