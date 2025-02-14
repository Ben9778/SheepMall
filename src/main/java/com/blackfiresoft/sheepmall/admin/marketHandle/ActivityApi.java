package com.blackfiresoft.sheepmall.admin.marketHandle;

import com.blackfiresoft.sheepmall.factory.ActivityFactory;
import com.blackfiresoft.sheepmall.market.Activity;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员活动操作接口
 */
@RestController
@RequestMapping("/57603")
public class ActivityApi {

    @Resource(name = "activityImp")
    private ActivityFactory activityFactory;

    @PostMapping("/create")
    public ResultEntity create(@RequestBody Activity activity) {
        if (activity == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return activityFactory.createActivity(activity);
    }

    @GetMapping("/delete/{activityId}")
    public ResultEntity deleteById(@PathVariable Long activityId, @RequestParam(value = "productNo") String productNo) {
        if (activityId == null || activityId <= 0 || productNo == null || productNo.isEmpty()) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return activityFactory.deleteActivity(activityId, productNo);
    }

    @PostMapping("/update/{activityId}")
    public ResultEntity updateStatus(@RequestParam(value = "status") String status, @PathVariable Long activityId) {
        if (status == null || status.isEmpty() || activityId == null || activityId < 0) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        activityFactory.updateActivityStatus(status,activityId);
        return ResultEntity.success();
    }

    @GetMapping("/all")
    public ResultEntity getAllActivity() {
        return ResultEntity.success(activityFactory.getAllActivity());
    }

    @GetMapping("/getByNo")
    public ResultEntity getByProductNo(@RequestParam(value = "productNo") String productNo) {
        return ResultEntity.success(activityFactory.getByProductNo(productNo));
    }

    @GetMapping("/getByStatus")
    public ResultEntity getByActivityStatus(@RequestParam(value = "status") String status) {
        return ResultEntity.success(activityFactory.getByStatus(status));
    }
}
