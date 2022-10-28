package com.shop.phoneshop.security.jwt;

import io.jsonwebtoken.Claims;

public class JwtUtils {
    public static JwtAuthentication getAuthentication(Claims claims) {
        final JwtAuthentication authentication = new JwtAuthentication();
        authentication.setUserId(Long.valueOf(claims.getSubject()));
        return authentication;
    }
}
