package com.blackfiresoft.sheepmall.payment.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UnifiedOrderRqs implements Serializable {

    @Serial
    private static final long serialVersionUID = 7012149104368800040L;
    private String openid;
    private Integer total;
    private String outTradeNo;
    private String description;
    private String attach;
}
