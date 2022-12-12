package com.shop.phoneshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.phoneshop.repos.ProductRepo;
import com.shop.phoneshop.requests.AddProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PhoneShopApplicationTests {
    private final MockMvc mockMvc;
    private final ProductRepo productRepo;

    @Autowired
    public PhoneShopApplicationTests(MockMvc mockMvc, ProductRepo productRepo) {
        this.mockMvc = mockMvc;
        this.productRepo = productRepo;
    }
    @Test
    void checkEmptyCart() throws Exception {
        mockMvc
                .perform(get("/api/cart").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(0));
    }

    @Test
    void notExistingProduct() throws Exception {
        mockMvc
                .perform(get("/api/catalog/product/{id}", 100).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    void addProduct() throws Exception {
        AddProductRequest request = new AddProductRequest();
        request.setProductId(1L);

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(request);

        mockMvc
                .perform(post("/api/addProduct").contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("user_product_1"))
                .andExpect(cookie().value("user_product_1",
                        "%7B%22amount%22%3A1%2C%22price%22%3A90000%2C%22pictureUrl%22%3A%22some+url%22%2C%22title%22%3A%22OpenPlus7Pro%22%7D"));
    }

    @Test
    void checkAllProducts() throws Exception {
        int productsCount = productRepo.findAll().size();

        mockMvc
                .perform(get("/api/catalog"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categoryCount").value(productsCount));
    }

    //TODO доделать тест с проверкой корзины и всего каталога
    @Test
    void checkAllProductsWithCart() throws Exception {
        int productsCount = productRepo.findAll().size();

        AddProductRequest request = new AddProductRequest();
        request.setProductId(3L);

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(request);

        mockMvc
                .perform(get("/api/catalog"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categoryCount").value(productsCount))
                .andExpect(jsonPath("$.cartCount").value(1L));
    }

    @Test
    void FailedTransaction() throws Exception {
        mockMvc
                .perform(post("/api/cart/transaction").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

}
