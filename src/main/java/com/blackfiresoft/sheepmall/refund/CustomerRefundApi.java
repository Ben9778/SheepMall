package com.blackfiresoft.sheepmall.refund;

import com.blackfiresoft.sheepmall.factory.RefundFactory;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 用户退款接口
 */
@RestController
@RequestMapping("/Api/refund")
public class CustomerRefundApi {

    @Resource(name="customerRefundImp")
    private RefundFactory refundFactory;

    @PostMapping("/create")
    public ResultEntity creatRefund(@RequestBody Refund refund) {
        if (refund != null) {
            return refundFactory.createRefund(refund);
        }
        return ResultEntity.fail(ResultEnum.PARAM_ERROR);
    }

    @GetMapping("/delete/{refundId}")
    public ResultEntity deleteRefund(@PathVariable Long refundId) {
        if (refundId != null ) {
            refundFactory.deleteRefund(refundId);
            return ResultEntity.success();
        }
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
    }

    @GetMapping("/cancel/{refundId}")
    public ResultEntity cancelRefund(@PathVariable Long refundId) {
        if (refundId != null) {
            refundFactory.cancelRefund(refundId);
            return ResultEntity.success();
        }
        return ResultEntity.fail(ResultEnum.PARAM_ERROR);
    }

    @GetMapping("/get/{userId}")
    public ResultEntity getByUser(@PathVariable Long userId) {
        if (userId != null) {
            return ResultEntity.success(refundFactory.getRefundByUserId(userId));
        }
        return ResultEntity.fail(ResultEnum.PARAM_ERROR);
    }
}
