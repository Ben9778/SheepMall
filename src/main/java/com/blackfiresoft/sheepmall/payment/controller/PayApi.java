package com.blackfiresoft.sheepmall.payment.controller;

import com.blackfiresoft.sheepmall.factory.OrderFactory;
import com.blackfiresoft.sheepmall.order.Orders;
import com.blackfiresoft.sheepmall.payment.InitPayProperty;
import com.blackfiresoft.sheepmall.payment.model.PrePayRqs;
import com.blackfiresoft.sheepmall.payment.model.UnifiedOrderRqs;
import com.blackfiresoft.sheepmall.payment.service.PayService;
import com.blackfiresoft.sheepmall.payment.util.SignUtil;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 支付下单API
 */
@RestController
@RequestMapping("/Api/pay")
public class PayApi {
    @Resource(name = "customerOrderImp")
    private OrderFactory orderFactory;

    @PostMapping("/unifiedOrder")
    public ResultEntity unifiedOrder(@RequestBody Orders orders, @RequestParam(value = "openId") String openid) {
        if (openid == null || orders == null || openid.isEmpty()) {
            ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        //订单创建入库
        Orders order=orderFactory.createOrder(orders);
        PrepayResponse prepayResponse = prePay(order, openid);
        PrePayRqs payInfo = getPayInfo(prepayResponse, order.getOrderNo());
        return ResultEntity.success(payInfo);
    }

    /**
     * 创建预支付
     */
    private PrepayResponse prePay(Orders orders,String openid) {
        BigDecimal amount = new BigDecimal(String.valueOf(orders.getTotalPrice()));
        UnifiedOrderRqs unifiedOrderRqs = new UnifiedOrderRqs();
        unifiedOrderRqs.setOpenid(openid);
        unifiedOrderRqs.setDescription(orders.getProductName());
        unifiedOrderRqs.setOutTradeNo(orders.getOrderNo());
        unifiedOrderRqs.setAttach("");
        unifiedOrderRqs.setTotal(amount.intValueExact());
        PayService payService = new PayService();
        return payService.prepay(unifiedOrderRqs);
    }

    /**
     * 得到支付参数前端发起支付
     */
    private PrePayRqs getPayInfo(PrepayResponse prepayResponse,String orderNo) {
        String appid= InitPayProperty.getPayProperty().get("appid");
        String timeValue = String.valueOf(System.currentTimeMillis() / 1000);
        String prepayId = "prepay_id=" + prepayResponse.getPrepayId();
        String nonceStr = UUID.randomUUID().toString().trim().replaceAll("-", "");
        String paySign = SignUtil.getSignWithBase64(appid, timeValue, nonceStr, prepayId);
        PrePayRqs rqs = new PrePayRqs();
        rqs.setPayOrderNo(orderNo);
        rqs.setNonceStr(nonceStr);
        rqs.setTimeStamp(timeValue);
        rqs.setPaySign(paySign);
        rqs.setPrepayId(prepayId);
        return rqs;
    }
}

