package com.blackfiresoft.sheepmall.payment.controller;

import com.blackfiresoft.sheepmall.payment.service.PayService;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.Status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Api/query")
public class QueryApi {
    /**
     * 查询支付订单状态
     */
    @GetMapping("/pay")
    public ResultEntity queryPay(String outTradNo){
        PayService payService = new PayService();
        Transaction transaction = payService.queryOrderByOutTradeNo(outTradNo);
        if(transaction == null){
            return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        Transaction.TradeStateEnum tradeState = transaction.getTradeState();
        return ResultEntity.success(tradeState.toString());
    }

    /**
     * 查询退款订单状态
     */
    @GetMapping("/refund")
    public ResultEntity queryRefund(String OutRefundNo){
        PayService payService = new PayService();
        Refund refund = payService.queryRefund(OutRefundNo);
        if(refund == null){
            return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        Status status = refund.getStatus();
        return ResultEntity.success(status.toString());
    }
}
