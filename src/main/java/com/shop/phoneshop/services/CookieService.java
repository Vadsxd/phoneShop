package com.shop.phoneshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CookieService {
    private final HttpServletResponse httpServletResponse;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public CookieService(HttpServletResponse httpServletResponse,
                         HttpServletRequest httpServletRequest
                         ){
        this.httpServletResponse = httpServletResponse;
        this.httpServletRequest = httpServletRequest;
    }

    public void setCookie(String name, String value) {
        int expiresInSeconds = (12 * 60 * 60);
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiresInSeconds);
        httpServletResponse.addCookie(cookie);
    }

    public Cookie[] getCookie(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getCookies();
    }
}
