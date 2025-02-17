package com.blackfiresoft.sheepmall.payment.controller;

import com.blackfiresoft.sheepmall.factory.OrderFactory;
import com.blackfiresoft.sheepmall.order.Orders;
import com.blackfiresoft.sheepmall.payment.util.NotifyUtil;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import com.blackfiresoft.sheepmall.util.StatusEnum;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.model.Transaction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 支付结果通知接口
 */
@RestController
@RequestMapping("/notify")
@Slf4j
public class PayNotifyApi {
    @Resource(name = "orderImp")
    private OrderFactory orderFactory;

    @PostMapping("/api")
    public ResultEntity paymentNotify(HttpServletRequest request, HttpServletResponse response) {
        RequestParam requestParam = NotifyUtil.requestParamConfig(request);
        try {
            Transaction transaction = NotifyUtil.notificationParserConfig().parse(requestParam, Transaction.class);
            if (Transaction.TradeStateEnum.SUCCESS.equals(transaction.getTradeState())) {
                //处理支付成功逻辑
                Orders orderByOrderNo = orderFactory.getOrderByOrderNo(transaction.getOutTradeNo());
                if (orderByOrderNo != null) {
                    //  更新订单状态
                    orderFactory.updateOrderStatus(orderByOrderNo.getId(), StatusEnum.COMPLETED.toString());
                } else {
                    log.error("订单号不存在：{}", transaction.getOutTradeNo());
                }
            }
        } catch (ValidationException e) {
            log.error("验证签名失败：{}",e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ResultEntity.fail(ResultEnum.ERROR);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        return ResultEntity.success();
    }
}
