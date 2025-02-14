package com.blackfiresoft.sheepmall.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserStatisticDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -692512243632349475L;

    private Integer todayUser;
    private Integer yesterdayUser;
}
