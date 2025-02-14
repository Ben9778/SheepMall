package com.blackfiresoft.sheepmall.receipt;
import com.blackfiresoft.sheepmall.user.UserRepository;
import com.blackfiresoft.sheepmall.user.Users;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class ReceiptImp implements ReceiptFactory {

    @Resource
    private ReceiptRepository receiptRepository;
    @Resource
    private UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createReceipt(Receipt receipt) {
        receiptRepository.saveAndFlush(receipt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteReceipt(Long receiptId) {
        receiptRepository.deleteById(receiptId);
    }

    @Override
    public List<Receipt> getReceiptByUserId(Long userId) {
        Optional<Users> byIdFetchReceipt = userRepository.findByIdFetchReceipt(userId);
        return byIdFetchReceipt.map(Users::getReceiptList).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReceipt(Long receiptId, Receipt receipt) {
        receiptRepository.findById(receiptId).ifPresent(r -> {
            r.setRecipient(receipt.getRecipient());
            r.setPhoneNumber(receipt.getPhoneNumber());
            r.setArea(receipt.getArea());
            r.setDetailedAddress(receipt.getDetailedAddress());
            r.setZipCode(receipt.getZipCode());
            receiptRepository.saveAndFlush(r);
        });
    }
}
