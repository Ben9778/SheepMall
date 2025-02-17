package com.blackfiresoft.sheepmall.admin.orderHandle;
import com.blackfiresoft.sheepmall.factory.RefundFactory;
import com.blackfiresoft.sheepmall.payment.model.RefundOrderRqs;
import com.blackfiresoft.sheepmall.payment.service.PayService;
import com.blackfiresoft.sheepmall.refund.Refund;
import com.blackfiresoft.sheepmall.refund.RefundRepository;
import com.blackfiresoft.sheepmall.user.Users;
import com.blackfiresoft.sheepmall.util.StatusEnum;
import com.wechat.pay.java.service.refund.model.Status;
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
            if(status.equals(StatusEnum.AGREE.toString())){
                //同意退款操作
                PayService payService = new PayService();
                RefundOrderRqs rqs = new RefundOrderRqs();
                rqs.setOutRefundNo(refund.getRefundNo());
                rqs.setOutTradNo(refund.getOrderNo());
                rqs.setTotal((long)refund.getRefundAmount().intValueExact());
                rqs.setRefund((long)refund.getRefundAmount().intValueExact());
                com.wechat.pay.java.service.refund.model.Refund refundResult = payService.create(rqs);
                if(refundResult.getStatus().equals(Status.SUCCESS)){
                    //退款成功
                    refund.setStatus(StatusEnum.REFUND_SUCCESS.toString());
                }
                refund.setStatus(StatusEnum.REFUNDING.toString());
            } else if (status.equals(StatusEnum.REJECTED.toString())) {
                //拒绝退款操作
                refund.setStatus(StatusEnum.REJECTED.toString());
            }
            refund.setStatus(status);
            refundRepository.saveAndFlush(refund);
        });
    }

    @Override
    public List<Refund> getRefundByUser(Users user) {
        return refundRepository.findByUsers(user);
    }
}
