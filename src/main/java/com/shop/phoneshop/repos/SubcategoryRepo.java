package com.shop.phoneshop.repos;

import com.shop.phoneshop.domain.Category;
import com.shop.phoneshop.domain.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepo extends JpaRepository<Subcategory, Long> {
    Subcategory findByTitle(String title);
}
