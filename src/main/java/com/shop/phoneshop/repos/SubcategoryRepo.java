package com.shop.phoneshop.repos;

import com.shop.phoneshop.domain.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryRepo extends JpaRepository<Subcategory, Long> {
}
