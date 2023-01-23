package com.shop.phoneshop.repos;

import com.shop.phoneshop.domain.ProductProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPropertyRepo extends JpaRepository<ProductProperty, Long> {
}
