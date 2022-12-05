package com.shop.phoneshop;

import com.shop.phoneshop.config.WebSecurityConfig;
import com.shop.phoneshop.security.jwt.JwtAuthEntryPoint;
import com.shop.phoneshop.security.jwt.JwtFilter;
import com.shop.phoneshop.security.jwt.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = {WebSecurityConfig.class, JwtAuthEntryPoint.class, JwtFilter.class, JwtProvider.class})
public class UpdatesTest {
    private final MockMvc mockMvc;

    @Autowired
    public UpdatesTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void giveCart() throws Exception {
        mockMvc
                .perform(get("/api/cart").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
