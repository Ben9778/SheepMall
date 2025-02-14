package com.blackfiresoft.sheepmall.admin.memberHandle;
import com.blackfiresoft.sheepmall.factory.AccountFactory;
import com.blackfiresoft.sheepmall.user.Users;
import org.springframework.data.domain.Page;


public interface Member extends AccountFactory {

    void addUser(Users user);

    void deleteUser(Long id);

    Page<Users> getAllUsers(int page, int size);
}
