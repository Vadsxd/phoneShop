package com.shop.phoneshop.repos;

import com.shop.phoneshop.domain.User;
import com.shop.phoneshop.domain.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProductRepo extends JpaRepository<UserProduct, Long> {
    List<UserProduct> findAllByUser(User user);
}
