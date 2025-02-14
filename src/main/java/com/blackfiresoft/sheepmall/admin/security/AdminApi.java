package com.blackfiresoft.sheepmall.admin.security;

import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/57596")
public class AdminApi {

    @Resource
    private AdminHandle adminHandle;

    @PostMapping("/updatePwd")
    public ResultEntity initUpdatePwd(@RequestParam(value = "adminId") Long adminId, @RequestParam(value = "oldPwd") String oldPwd,
                                      @RequestParam(value = "newPwd") String newPwd) {
        if (adminId == null || oldPwd == null || newPwd == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return adminHandle.initUpdatePwd(adminId, oldPwd, newPwd);
    }

    @PostMapping("/login/u")
    public ResultEntity loginByUsername(HttpServletRequest request, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        if (username == null || password == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        Administrator administrator = adminHandle.loginByUsername(username, password);
        if (administrator != null) {
            request.getSession().setAttribute("admin", username);
            return ResultEntity.success(administrator);
        }
        return ResultEntity.fail(ResultEnum.LOGIN_FAILED);
    }

    @PostMapping("/login/e")
    public ResultEntity loginByEmail(HttpServletRequest request, @RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        if (email == null || password == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        Administrator administrator = adminHandle.loginByEmail(email, password);
        if (administrator!=null) {
            request.getSession().setAttribute("admin", email);
            return ResultEntity.success(administrator);
        }
        return ResultEntity.fail(ResultEnum.LOGIN_FAILED);
    }

    @GetMapping("/getInfo/{adminId}")
    public ResultEntity findAdminInfo(@PathVariable(value = "adminId") Long adminId) {
        if (adminId == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        Administrator adminInfo = adminHandle.getAdminInfo(adminId);
        if (adminInfo != null) {
            return ResultEntity.success(adminInfo);
        }
        return ResultEntity.fail(ResultEnum.ADMIN_NOT_FOUND);
    }

    @PostMapping("/resetPwd")
    public ResultEntity forgotAndResetPwd(@RequestParam(value = "code") String code, @RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        if (code == null || email == null || password == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return adminHandle.resetPwd(code, email, password);
    }

    @PostMapping("/getCode")
    public ResultEntity getResetCodeByEmail(@RequestParam(value = "email") String email) {
        if (email == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
       if(!adminHandle.getValidationCode(email)){
           return ResultEntity.fail(ResultEnum.SEND_EMAIL_ERROR);
       }
        return ResultEntity.success();
    }
}
