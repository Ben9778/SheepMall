package com.blackfiresoft.sheepmall.payment.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RefundOrderRqs implements Serializable {

    @Serial
    private static final long serialVersionUID = 139434275750901434L;
    private String outTradNo;
    private String outRefundNo;
    private Long refund;
    private Long total;
}
