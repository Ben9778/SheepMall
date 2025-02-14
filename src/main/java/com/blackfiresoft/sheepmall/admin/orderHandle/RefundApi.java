package com.blackfiresoft.sheepmall.admin.orderHandle;

import com.blackfiresoft.sheepmall.factory.RefundFactory;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import com.blackfiresoft.sheepmall.user.Users;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员操作退款接口
 */
@RestController
@RequestMapping("/57599")
public class RefundApi {

    @Resource(name = "refundImp")
    private RefundFactory refundFactory;

    @GetMapping("/all")
    public ResultEntity allRefund() {
        return ResultEntity.success(refundFactory.getAllRefund());
    }

    @PostMapping("/byRefundNo")
    public ResultEntity getByRefundNo(@RequestParam("refundNo") String refundNo) {
        if (refundNo == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return ResultEntity.success(refundFactory.getRefundByRefundNo(refundNo));
    }

    @PostMapping("/byOrderNo")
    public ResultEntity getByOrderNo(@RequestParam("orderNo") String orderNo) {
        if (orderNo == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return ResultEntity.success(refundFactory.getRefundByOrderNo(orderNo));
    }

    @PostMapping("/byStatus")
    public ResultEntity getByStatus(@RequestParam("status") String status) {
        if (status == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return ResultEntity.success(refundFactory.getRefundByStatus(status));
    }

    @PostMapping("/updateStatus")
    public ResultEntity updateStatus(@RequestParam(value = "refundId") Long refundId, @RequestParam(value = "status")String status) {
        if (refundId == null || status == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        refundFactory.updateRefundStatus(refundId, status);
        return ResultEntity.success();
    }

    @PostMapping("/byUser")
    public ResultEntity getByUser(@RequestBody Users user) {
        if (user == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return ResultEntity.success(refundFactory.getRefundByUser(user));
    }
}
