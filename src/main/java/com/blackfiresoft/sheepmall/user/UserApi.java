package com.blackfiresoft.sheepmall.user;

import com.blackfiresoft.sheepmall.dto.UserDto;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import com.blackfiresoft.sheepmall.util.DataTransfer;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Api/users")
public class UserApi {
    @Resource
    private UserService userService;

    @PostMapping("/validation")
    public ResultEntity getValidationCode(@RequestParam(value = "email") String email) {
        if (email == null || email.isEmpty()) {
            ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        if(!userService.getValidationCode(email)){
            ResultEntity.fail(ResultEnum.SEND_EMAIL_ERROR);
        }
        return ResultEntity.success();
    }

    @PostMapping("/register")
    public ResultEntity registerUser(@RequestBody Users user, @RequestParam(value = "code") String code) {
        return userService.register(user, code);
    }

    @PostMapping("/login/n")
    public ResultEntity loginByNameAndPassword(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        Boolean result = userService.loginByUsername(username, password);
        if (result) {
            return ResultEntity.success();
        }
        return ResultEntity.fail(ResultEnum.LOGIN_FAILED);
    }

    @PostMapping("/login/e")
    public ResultEntity loginByEmailAndPassword(@RequestParam("email") String email, @RequestParam("password") String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        Boolean result = userService.loginByEmail(email, password);
        if (result) {
            return ResultEntity.success();
        }
        return ResultEntity.fail(ResultEnum.LOGIN_FAILED);
    }

    @PostMapping("/update")
    public ResultEntity updateUser(@RequestBody Users user) {
        if (user == null) {
            ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        userService.updateUser(user);
        return ResultEntity.success();
    }

    @PostMapping("/resetPwd")
    public ResultEntity resetPassword(@RequestParam(value = "email") String email, @RequestParam(value = "code") String code,
                                      @RequestParam(value = "password") String password) {
        if (email == null || email.isEmpty() || code == null || code.isEmpty() || password == null || password.isEmpty()) {
            ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return userService.resetPassword(email, code, password);
    }

    @GetMapping("/logout")
    public ResultEntity logout(@RequestParam("userId") Long userId, @RequestParam("email") String email, @RequestParam("code") String code) {
        if (userId == null || email == null || code == null || code.isEmpty()) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return userService.deleteUser(userId, email, code);
    }

    @GetMapping("/getUser/{userId}")
    public ResultEntity getUserInfo(@PathVariable Long userId) {
        if (userId == null || userId <= 0) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        Optional<Users> userInfo = userService.getUserInfo(userId);
        return userInfo.map(users -> ResultEntity.success(DataTransfer.transfer(new UserDto(), users))).orElseGet(() -> ResultEntity.fail(ResultEnum.USER_NOT_EXIST));
    }
}

