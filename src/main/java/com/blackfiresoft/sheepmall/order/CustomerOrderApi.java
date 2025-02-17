package com.blackfiresoft.sheepmall.order;

import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import com.blackfiresoft.sheepmall.factory.OrderFactory;
import com.blackfiresoft.sheepmall.user.Users;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户操作订单接口
 */
@RestController
@RequestMapping("/Api/orders")
public class CustomerOrderApi {
    @Resource(name = "customerOrderImp")
    private OrderFactory orderFactory;

    @PostMapping("/byUser")
    public ResultEntity getByUser(@RequestBody Users user) {
        if (user == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        List<Orders> orders = orderFactory.getOrdersByUser(user);
        if (orders == null || orders.isEmpty()) {
            return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        return ResultEntity.success(orders);
    }

    @PostMapping("/byStatus")
    public ResultEntity getByUserAndStatus(@RequestBody Users user, @RequestParam("status") String status) {
        if (user == null || status == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        List<Orders> orders = orderFactory.getOrdersByUserAndStatus(user, status);
        if (orders == null || orders.isEmpty()) {
            return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        return ResultEntity.success(orders);
    }

    @PostMapping("/delete")
    public ResultEntity deleteOrders(@RequestBody Long[] orderIds) {
        if (orderIds == null || orderIds.length == 0) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        orderFactory.deleteOrders(orderIds);
        return ResultEntity.success();
    }

    @GetMapping("/updateStatus/{orderId}/{type}")
    public ResultEntity cancelOrder(@PathVariable Long orderId, @PathVariable int type) {
        if (orderId == null || orderId <= 0 || type < 0) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        orderFactory.updateOrderStatus(orderId,type);
        return ResultEntity.success();
    }
}
