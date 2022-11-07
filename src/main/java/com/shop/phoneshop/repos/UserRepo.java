package com.shop.phoneshop.repos;

import com.shop.phoneshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Boolean existsByEmail(String Email);

    User findByEmail(String Email);
}
