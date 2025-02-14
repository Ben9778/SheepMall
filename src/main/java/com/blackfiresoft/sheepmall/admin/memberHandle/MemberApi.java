package com.blackfiresoft.sheepmall.admin.memberHandle;
import com.blackfiresoft.sheepmall.dto.UserDto;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import com.blackfiresoft.sheepmall.user.Users;
import com.blackfiresoft.sheepmall.util.DataTransfer;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/57601")
public class MemberApi {

    @Resource
    private Member member;

    @PostMapping("/addUser")
    public ResultEntity addUser(@RequestBody Users user) {
        if (user == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        member.addUser(user);
        return ResultEntity.success();
    }

    @GetMapping("/deleteUser/{id}")
    public ResultEntity deleteUser(@PathVariable Long id) {
        if (id == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        member.deleteUser(id);
        return ResultEntity.success();
    }

    @GetMapping("/getAllUsers")
    public ResultEntity getAllUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "15") int size) {

        Page<Users> userResult = member.getAllUsers(page, size);
        if (userResult.isEmpty()) {
            return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        List<UserDto> userDtoList = new ArrayList<>();
        for (Users user : userResult.getContent()) {
            UserDto userDto = DataTransfer.transfer(new UserDto(), user);
            userDtoList.add(userDto);
        }
        Page<UserDto> userDtoResult = new PageImpl<>(userDtoList, userResult.getPageable(), userResult.getTotalElements());
        return ResultEntity.success(userDtoResult.getContent());
    }

    @PostMapping("/updateUser")
    public ResultEntity updateUser(@RequestBody Users user){
        if (user == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        member.updateUser(user);
        return ResultEntity.success();
    }
}
