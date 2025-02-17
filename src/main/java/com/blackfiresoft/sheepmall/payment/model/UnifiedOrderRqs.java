package com.blackfiresoft.sheepmall.payment.model;

import lombok.Data;

@Data
public class UnifiedOrderRqs {

    private String openid;
    private Integer total;
    private String outTradeNo;
    private String description;
    private String attach;
}
