package com.shop.phoneshop.repos;

import com.shop.phoneshop.domain.Photo;
import com.shop.phoneshop.domain.UserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepo extends JpaRepository<Photo, Long> {
    public List<Photo> getAllByUserFeedback(UserFeedback userFeedback);
}
