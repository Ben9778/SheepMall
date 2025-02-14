package com.blackfiresoft.sheepmall.factory;

import com.blackfiresoft.sheepmall.refund.Refund;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.user.Users;

import java.util.List;

public interface RefundFactory {

    default ResultEntity createRefund(Refund refund) {
        return null;
    }

    default void deleteRefund(Long refundId) {
    }

    default void cancelRefund(Long refundId) {
    }

    default List<Refund> getRefundByUserId(Long userId) {
        return null;
    }

    default List<Refund> getRefundByUser(Users user) {
        return null;
    }

    default List<Refund> getAllRefund() {
        return null;
    }

    default Refund getRefundByRefundNo(String refundNo) {
        return null;
    }

    default Refund getRefundByOrderNo(String orderNo) {
        return null;
    }

    default List<Refund> getRefundByStatus(String status) {
        return null;
    }

    default void updateRefundStatus(Long refundId, String status) {

    }
}
