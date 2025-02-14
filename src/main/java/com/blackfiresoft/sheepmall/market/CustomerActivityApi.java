package com.blackfiresoft.sheepmall.market;

import com.blackfiresoft.sheepmall.dto.ActivityDto;
import com.blackfiresoft.sheepmall.factory.ActivityFactory;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 用户活动操作接口
 */
@RestController
@RequestMapping("/Api/activity")
public class CustomerActivityApi {
    @Resource(name = "customerActivityImp")
    private ActivityFactory activityFactory;

    @PostMapping("/rush")
    public ResultEntity rushSale(@RequestBody ActivityDto activityDto) {
        if(activityDto == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return activityFactory.rushSale(activityDto);
    }

    @GetMapping("/byStatus")
    public ResultEntity getByStatus(@RequestParam(value = "status") String status){
        if(status == null || status.isEmpty()) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return ResultEntity.success(activityFactory.getByStatus(status));
    }
}
