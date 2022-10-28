package com.shop.phoneshop.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private Instant expiresIn;
}
