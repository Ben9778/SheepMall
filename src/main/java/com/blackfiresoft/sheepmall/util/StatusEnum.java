package com.blackfiresoft.sheepmall.util;

import lombok.Getter;

@Getter
public enum StatusEnum {
    COMPLETED("已完成"),
    UNPAID("未支付"),
    CANCELLED("已取消"),
    CANCEL_APPLY("取消申请"),
    WAIT_VERIFY("待审核"),
    REFUNDING("退款中"),
    REFUND_SUCCESS("已退款"),
    REFUND_FAILED("退款失败"),
    REJECTED("拒绝");

    private final String description;

    StatusEnum(String description) {
        this.description = description;
    }

    public String toString() {
        return description;
    }
}
