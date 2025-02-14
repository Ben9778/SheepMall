package com.blackfiresoft.sheepmall.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RefundStatisticDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 6339243835328504999L;

    private BigDecimal todayRefund;

    private BigDecimal yesterdayRefund;
}
