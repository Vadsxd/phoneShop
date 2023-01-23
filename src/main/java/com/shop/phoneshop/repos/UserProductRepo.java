package com.shop.phoneshop.repos;

import com.shop.phoneshop.domain.Product;
import com.shop.phoneshop.domain.User;
import com.shop.phoneshop.domain.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProductRepo extends JpaRepository<UserProduct, Long> {
    List<UserProduct> findAllByUser(User user);

    void deleteAllByUser(User user);

    Optional<UserProduct> findByProductAndUser(Product product, User user);

    Boolean existsByProductTitleAndUser(String title, User user);
}
