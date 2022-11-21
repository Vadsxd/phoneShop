package com.shop.phoneshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class CookieService {
    private final HttpServletResponse httpServletResponse;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public CookieService(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest){
        this.httpServletResponse = httpServletResponse;
        this.httpServletRequest = httpServletRequest;
    }

    public void setCookie(String name, String value) {
        int expiresInSeconds = (6 * 60 * 60);
        Cookie cookie = new Cookie(name, URLEncoder.encode(value, StandardCharsets.UTF_8));
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(expiresInSeconds);
        httpServletResponse.addCookie(cookie);
    }

    public void deleteCookie(String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
    }

    public Cookie[] getCookies() {
        return httpServletRequest.getCookies();
    }

    public Cookie getCookie(String name) {
        return Arrays.stream(httpServletRequest.getCookies()).filter(c -> c.getName().equals(name)).toList().get(0);
    }
}
