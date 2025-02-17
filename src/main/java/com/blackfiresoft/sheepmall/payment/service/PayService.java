package com.blackfiresoft.sheepmall.payment.service;
import com.blackfiresoft.sheepmall.payment.InitPayProperty;
import com.blackfiresoft.sheepmall.payment.model.RefundOrderRqs;
import com.blackfiresoft.sheepmall.payment.model.UnifiedOrderRqs;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.AmountReq;
import com.wechat.pay.java.service.refund.model.CreateRequest;
import com.wechat.pay.java.service.refund.model.QueryByOutRefundNoRequest;
import com.wechat.pay.java.service.refund.model.Refund;

/**
 * 支付服务
 */
public class PayService {

    private final JsapiService service;
    private final RefundService refundService;

    public PayService() {
        Config config = new RSAAutoCertificateConfig.Builder()
                .merchantId(InitPayProperty.getPayProperty().get("merchantId"))
                .privateKeyFromPath(InitPayProperty.getPayProperty().get("privateKeyPath"))
                .merchantSerialNumber(InitPayProperty.getPayProperty().get("merchantSerialNumber"))
                .apiV3Key(InitPayProperty.getPayProperty().get("apiV3Key"))
                .build();
        service = new JsapiService.Builder().config(config).build();
        refundService=new RefundService.Builder().config(config).build();
    }

    /**
     * 关闭订单
     */
    public void closeOrder(String outTradNo) {
        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(InitPayProperty.getPayProperty().get("merchantId"));
        request.setOutTradeNo(outTradNo);
        service.closeOrder(request);
    }

    /**
     * 支付下单
     */
    public PrepayResponse prepay(UnifiedOrderRqs rqs) {
        PrepayRequest request = new PrepayRequest();
        Payer payer=new Payer();
        Amount amount=new Amount();
        payer.setOpenid(rqs.getOpenid());
        amount.setTotal(rqs.getTotal());
        request.setAppid(InitPayProperty.getPayProperty().get("appId"));
        request.setMchid(InitPayProperty.getPayProperty().get("merchantId"));
        request.setNotifyUrl(InitPayProperty.getPayProperty().get("notifyUrl"));
        request.setOutTradeNo(rqs.getOutTradeNo());
        request.setDescription(rqs.getDescription());
        request.setAmount(amount);
        request.setPayer(payer);
        request.setAttach(rqs.getAttach());
        return service.prepay(request);
    }

    /**
     * 商户订单号查询订单
     */
    public Transaction queryOrderByOutTradeNo(String outTradNo) {

        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(InitPayProperty.getPayProperty().get("merchantId"));
        request.setOutTradeNo(outTradNo);
        return service.queryOrderByOutTradeNo(request);
    }

    /**
     * 退款
     */
    public Refund create(RefundOrderRqs rqs){
        CreateRequest request=new CreateRequest();
        AmountReq req=new AmountReq();
        req.setCurrency("CNY");
        req.setRefund(rqs.getRefund());
        req.setTotal(rqs.getTotal());
        request.setOutTradeNo(rqs.getOutTradNo());
        request.setOutRefundNo(rqs.getOutRefundNo());
        request.setNotifyUrl(InitPayProperty.getPayProperty().get("refundNotifyUrl"));
        request.setAmount(req);
        return refundService.create(request);
    }

    /**
     * 查询单笔退款（通过商户退款单号）
     */
    public Refund queryRefund(String OutRefundNo){
        QueryByOutRefundNoRequest request = new QueryByOutRefundNoRequest();
        request.setOutRefundNo(OutRefundNo);
        return refundService.queryByOutRefundNo(request);
    }
}
