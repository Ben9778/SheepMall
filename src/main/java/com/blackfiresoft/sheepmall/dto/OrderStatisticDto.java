package com.blackfiresoft.sheepmall.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderStatisticDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 3548650236953819501L;

    private Integer todaySale;

    private Integer yesterdaySale;

    private BigDecimal todayIncome;

    private BigDecimal yesterdayIncome;

}
