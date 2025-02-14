package com.blackfiresoft.sheepmall.receipt;

import java.util.List;

public interface ReceiptFactory {

    void createReceipt(Receipt receipt);

    void deleteReceipt(Long receiptId);

    List<Receipt> getReceiptByUserId(Long userId);

    void updateReceipt(Long receiptId, Receipt receipt);

}
