package com.shop.phoneshop;

import com.shop.phoneshop.domain.Product;
import com.shop.phoneshop.domain.User;
import com.shop.phoneshop.domain.UserProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UpdatesTest {
    private final MockMvc mockMvc;

    @Autowired
    public UpdatesTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void addedAmount() throws Exception {
        User user = new User();
        user.setLastName("Lucich");
        user.setFirstName("Kolya");
        user.setPassword("123");
        user.setEmail("papa@mail.ru");

        Product product = new Product();
        product.setTitle("Apple");
        product.setDescription("YEEEEES");
        product.setAmount(3L);
        product.setPrice(10000L);
        product.setDiscount(true);
        product.setDiscountPrice(90000L);
        product.setPictureUrl("url");

        UserProduct userProduct = new UserProduct();
        userProduct.setUser(user);
        userProduct.setAmount(2L);
        userProduct.setProduct(product);

        mockMvc
                .perform(putRequest(request))
    }

}
