package com.blackfiresoft.sheepmall.payment.controller;

import com.blackfiresoft.sheepmall.factory.OrderFactory;
import com.blackfiresoft.sheepmall.factory.RefundFactory;
import com.blackfiresoft.sheepmall.payment.util.NotifyUtil;
import com.blackfiresoft.sheepmall.refund.Refund;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import com.blackfiresoft.sheepmall.util.StatusEnum;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.refund.model.RefundNotification;
import com.wechat.pay.java.service.refund.model.Status;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 退款通知操作API
 */
@RestController
@RequestMapping("/refund-notify")
@Slf4j
public class RefundNotifyApi {
    @Resource(name = "orderImp")
    private OrderFactory orderFactory;
    @Resource(name = "refundImp")
    private RefundFactory refundFactory;

    @PostMapping("/api")
    public ResultEntity refundNotify(HttpServletRequest request, HttpServletResponse response) {

        RequestParam requestParam= NotifyUtil.requestParamConfig(request);
        try {
            RefundNotification refundNotification = NotifyUtil.notificationParserConfig().parse(requestParam, RefundNotification.class);
            if(Status.SUCCESS.equals(refundNotification.getRefundStatus())){
                //退款成功修改订单状态和退款单状态
                Refund refundByRefundNo = refundFactory.getRefundByRefundNo(refundNotification.getOutTradeNo());
                refundFactory.updateRefundStatus(refundByRefundNo.getId(), StatusEnum.REFUND_SUCCESS.toString());
                orderFactory.updateOrderStatus(refundByRefundNo.getOrderNo(), StatusEnum.REFUND_SUCCESS.toString());
            }
        } catch (ValidationException e) {
            log.error("sign verification failed, {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ResultEntity.fail(ResultEnum.ERROR);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        return ResultEntity.success();
    }
}
