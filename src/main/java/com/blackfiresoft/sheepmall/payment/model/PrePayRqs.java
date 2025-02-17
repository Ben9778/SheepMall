package com.blackfiresoft.sheepmall.payment.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PrePayRqs implements Serializable {

    @Serial
    private static final long serialVersionUID = 7504845135913171695L;
    private String timeStamp;
    private String nonceStr;
    private String prepayId;
    private String payOrderNo;
    private String paySign;
}
