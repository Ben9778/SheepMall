package com.blackfiresoft.sheepmall.payment.util;

import com.blackfiresoft.sheepmall.payment.InitPayProperty;
import com.wechat.pay.java.core.util.PemUtil;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

/**
 * 小程序支付参数签名工具
 */
public class SignUtil {

    public static String getSignWithBase64(String appid, String time, String nonceStr, String prepayId) {

        String data = appid + "\n" + time + "\n" + nonceStr + "\n" + prepayId + "\n";

        try {
            PrivateKey privateKey = loadPrivateKey();
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            byte[] signatureBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    protected static PrivateKey loadPrivateKey() {

        return PemUtil.loadPrivateKeyFromPath(InitPayProperty.getPayProperty().get("privateKeyPath"));
    }
}
