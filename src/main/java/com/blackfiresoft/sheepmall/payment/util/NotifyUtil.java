package com.blackfiresoft.sheepmall.payment.util;

import com.blackfiresoft.sheepmall.payment.InitPayProperty;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import jakarta.servlet.http.HttpServletRequest;

public class NotifyUtil {

    public static NotificationParser notificationParserConfig(){
        NotificationConfig config = new RSAAutoCertificateConfig.Builder()
                .merchantId(InitPayProperty.getPayProperty().get("merchantId"))
                .privateKeyFromPath(InitPayProperty.getPayProperty().get("privateKeyPath"))
                .merchantSerialNumber(InitPayProperty.getPayProperty().get("merchantSerialNumber"))
                .apiV3Key(InitPayProperty.getPayProperty().get("apiV3Key"))
                .build();
        return new NotificationParser(config);
    }

    public static RequestParam requestParamConfig(HttpServletRequest request){
        String wechatPaySerial=request.getHeader("Wechatpay-Serial");
        String wechatpayNonce=request.getHeader("Wechatpay-Nonce");
        String wechatSignature=request.getHeader("Wechatpay-Signature");
        String wechatTimestamp=request.getHeader("Wechatpay-Timestamp");
        String body= HttpUtil.parseRequestHeader(request);

        return new RequestParam.Builder()
                .serialNumber(wechatPaySerial)
                .nonce(wechatpayNonce)
                .signature(wechatSignature)
                .timestamp(wechatTimestamp)
                .body(body)
                .build();
    }
}
