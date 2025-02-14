package com.blackfiresoft.sheepmall.refund;

import com.blackfiresoft.sheepmall.factory.RefundFactory;
import com.blackfiresoft.sheepmall.order.OrderRepository;
import com.blackfiresoft.sheepmall.order.Orders;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import com.blackfiresoft.sheepmall.user.UserRepository;
import com.blackfiresoft.sheepmall.user.Users;
import com.blackfiresoft.sheepmall.util.StatusEnum;
import com.blackfiresoft.sheepmall.util.TimeTransfer;
import jakarta.annotation.Resource;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 用户退款操作实现类
 */
@Service("customerRefundImp")
public class CustomerRefundImp implements RefundFactory {

    @Resource
    private RefundRepository refundRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private OrderRepository orderRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultEntity createRefund(Refund refund) {
        Orders byOrderNo = orderRepository.findByOrderNo(refund.getOrderNo());
        //找不到订单
        if (byOrderNo == null) {
            return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        if(!byOrderNo.getStatus().equals(StatusEnum.COMPLETED.toString())){
            return ResultEntity.fail(ResultEnum.REFUND_FAILED);
        }
        Timestamp payTime = TimeTransfer.StringToTimestamp(byOrderNo.getPayment_at());
        long timeDifference = (System.currentTimeMillis() - payTime.getTime()) / (24 * 60 * 60 * 1000);
        //超过付款时间7天不能退款
        if (timeDifference > 7) {
            return ResultEntity.fail(ResultEnum.REFUND_EXPIRED);
        }
        //生成退款订单
        UUID uuid = UUID.randomUUID();
        refund.setRefundNo(uuid.toString().replace("-", ""));
        refundRepository.saveAndFlush(refund);
        //更新订单状态
        byOrderNo.setStatus(StatusEnum.REFUNDING.toString());
        orderRepository.saveAndFlush(byOrderNo);
        return ResultEntity.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRefund(Long refundId) {
        refundRepository.deleteById(refundId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelRefund(Long refundId) {
        refundRepository.findById(refundId).ifPresent(refund -> {
            refund.setStatus(StatusEnum.CANCELLED.toString());
            refundRepository.saveAndFlush(refund);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Refund> getRefundByUserId(Long userId) {
        Optional<Users> byId = userRepository.findById(userId);
        if (byId.isPresent()) {
            Hibernate.initialize(byId.get().getRefundList());
            return byId.get().getRefundList();
        }
        return null;
    }
}
