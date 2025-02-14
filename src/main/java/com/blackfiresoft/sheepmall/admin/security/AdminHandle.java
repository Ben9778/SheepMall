package com.blackfiresoft.sheepmall.admin.security;

import com.blackfiresoft.sheepmall.factory.AccountFactory;
import com.blackfiresoft.sheepmall.result.ResultEntity;


public interface AdminHandle extends AccountFactory {

    ResultEntity initUpdatePwd(Long adminId, String oldPwd, String newPwd);

    Administrator loginByUsername(String username, String password);

    Administrator loginByEmail(String email, String password);

    Administrator getAdminInfo(Long adminId);

    ResultEntity resetPwd(String email, String code, String password);

}
