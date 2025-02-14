package com.blackfiresoft.sheepmall.factory;

import com.blackfiresoft.sheepmall.user.Users;

public interface AccountFactory {

    default void updateUser(Users user) {
    }

    default Boolean getValidationCode(String email) {
        return null;
    }
}
