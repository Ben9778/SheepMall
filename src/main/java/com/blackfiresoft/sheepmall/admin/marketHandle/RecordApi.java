package com.blackfiresoft.sheepmall.admin.marketHandle;

import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import com.blackfiresoft.sheepmall.user.Users;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 活动记录管理接口
 */
@RestController
@RequestMapping("/57604")
public class RecordApi {

    @Resource
    private RecordService recordService;

    @GetMapping("/byId/{activityId}")
    public ResultEntity getById(@PathVariable Long activityId) {
        if(activityId==null || activityId<=0){
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return ResultEntity.success(recordService.getByActivityId(activityId));
    }

    @GetMapping("/byNo")
    public ResultEntity getByNo(@RequestParam(value = "productNo") String productNo) {
        return ResultEntity.success(recordService.getByProductNo(productNo));
    }

    @PostMapping("/byUser")
    public ResultEntity getByUser(@RequestBody Users user) {
        if(user==null){
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return ResultEntity.success(recordService.getByUsers(user));
    }
}
