package com.shop.phoneshop.utils;

import com.shop.phoneshop.domain.User;

public interface CatalogUtil {
    static String getInitials(User user) {
        if (user != null) {
            return user.getLastName() + ". " + user.getFirstName().charAt(0);
        }
        return null;
    }
}
