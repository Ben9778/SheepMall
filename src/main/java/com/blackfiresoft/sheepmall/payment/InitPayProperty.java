package com.blackfiresoft.sheepmall.payment;

import lombok.Getter;
import org.springframework.util.Assert;
import java.util.HashMap;
import java.util.Map;

public class InitPayProperty {

    @Getter
    private static Map<String, String> payProperty;

    static {
        payProperty = new HashMap<>();
    }

    private InitPayProperty() {}

    public static void setPayProperty(PayRqs payRqs) {
        Assert.notNull(payRqs, "payRqs cannot be null");
        payProperty.put("appid", payRqs.getAppid());
        payProperty.put("merchantId", payRqs.getMerchantId());
        payProperty.put("privateKeyPath", payRqs.getPrivateKeyPath());
        payProperty.put("merchantSerialNumber", payRqs.getMerchantSerialNumber());
        payProperty.put("apiV3Key", payRqs.getApiV3Key());
        payProperty.put("notifyUrl", payRqs.getNotifyUrl());
        payProperty.put("returnNotifyUrl", payRqs.getRefundNotifyUrl());
    }

}
