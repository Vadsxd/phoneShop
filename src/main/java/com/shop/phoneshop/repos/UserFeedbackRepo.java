package com.shop.phoneshop.repos;

import com.shop.phoneshop.domain.UserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeedbackRepo extends JpaRepository<UserFeedback, Long> {
}
