package com.blackfiresoft.sheepmall.admin.orderHandle;
import com.blackfiresoft.sheepmall.factory.RefundFactory;
import com.blackfiresoft.sheepmall.refund.Refund;
import com.blackfiresoft.sheepmall.refund.RefundRepository;
import com.blackfiresoft.sheepmall.user.Users;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 管理员操作退款的实现类
 */
@Service("refundImp")
public class RefundImp implements RefundFactory {

    @Resource
    private RefundRepository refundRepository;

    @Override
    public List<Refund> getAllRefund() {
        return refundRepository.findAll();
    }

    @Override
    public Refund getRefundByRefundNo(String refundNo) {
        return refundRepository.findByRefundNo(refundNo);
    }

    @Override
    public Refund getRefundByOrderNo(String orderNo) {
        return refundRepository.findByOrderNo(orderNo);
    }

    @Override
    public List<Refund> getRefundByStatus(String status) {
        return refundRepository.findByStatus(status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRefundStatus(Long refundId,String status) {
        refundRepository.findById(refundId).ifPresent(refund -> {
            refund.setStatus(status);
            refundRepository.saveAndFlush(refund);
        });
    }

    @Override
    public List<Refund> getRefundByUser(Users user) {
        return refundRepository.findByUsers(user);
    }
}
