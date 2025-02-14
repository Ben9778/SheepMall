package com.blackfiresoft.sheepmall.user;


import com.blackfiresoft.sheepmall.factory.AccountFactory;
import com.blackfiresoft.sheepmall.result.ResultEntity;

import java.util.Optional;

public interface UserService extends AccountFactory {

    ResultEntity register(Users user, String code);

    ResultEntity deleteUser(Long id, String email, String code);

    ResultEntity resetPassword(String email, String code, String password);

    Boolean loginByEmail(String email, String password);

    Boolean loginByUsername(String username, String password);

    Optional<Users> getUserInfo(Long userId);


}
