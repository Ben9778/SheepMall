package com.blackfiresoft.sheepmall.admin.orderHandle;

import com.blackfiresoft.sheepmall.factory.OrderFactory;
import com.blackfiresoft.sheepmall.order.Orders;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 管理员操作订单
 */
@RestController
@RequestMapping("/57600")
public class OrderApi {

    @Resource(name = "orderImp")
    private OrderFactory orderFactory;

    @PostMapping("/byStatus")
    public ResultEntity getOrdersByStatus(@RequestParam(value = "status") String status,
                                          @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        if (status == null || status.isEmpty() || pageNo < 0 || pageSize < 1) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return ResultEntity.success(orderFactory.getOrdersByStatus(status, pageNo, pageSize).getContent());
    }

    @PostMapping("/byUsername")
    public ResultEntity getOrdersByUsername(@RequestParam(value = "username") String username) {
        if (username == null || username.isEmpty()) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        List<Orders> orders = orderFactory.getOrdersByUsername(username);
        if (orders == null || orders.isEmpty()) {
            return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        return ResultEntity.success(orders);
    }

    @PostMapping("/updateStatus")
    public ResultEntity updateOrderStatus(@RequestParam("orderId") Long orderId, @RequestParam("status") String status) {
        if (orderId == null || orderId < 1 || status == null || status.isEmpty()) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        orderFactory.updateOrderStatus(orderId, status);
        return ResultEntity.success();
    }

    @PostMapping("/byOrderNo")
    public ResultEntity getOrderByOrderNo(@RequestParam("orderNo") String orderNo) {
        if (orderNo == null || orderNo.isEmpty()) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return orderFactory.getOrderByOrderNo(orderNo) == null ? ResultEntity.fail(ResultEnum.DATA_NOT_FOUND) : ResultEntity.success(orderFactory.getOrderByOrderNo(orderNo));
    }

    @GetMapping("/allOrders/{pageNo}/{pageSize}")
    public ResultEntity getAllOrders(@PathVariable int pageNo, @PathVariable int pageSize) {
        if (pageNo < 0 || pageSize < 1) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return ResultEntity.success(orderFactory.getAllOrders(pageNo, pageSize).getContent());
    }

    @PostMapping("/byDateRange")
    public ResultEntity getOrdersByDateRange(@RequestParam(value = "startDate") String startDate,
                                             @RequestParam(value = "endDate") String endDate,
                                             @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        if (startDate == null || endDate == null || pageNo < 0 || pageSize < 1) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return ResultEntity.success(orderFactory.getOrdersByDateRange(startDate, endDate, pageNo, pageSize).getContent());
    }

    @PostMapping("/updateOrder/{orderId}")
    public ResultEntity updateOrder(@PathVariable Long orderId, @RequestBody Orders order) {
        if (orderId == null || orderId < 1 || order == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        orderFactory.updateOrder(orderId, order);
        return ResultEntity.success();
    }
}
