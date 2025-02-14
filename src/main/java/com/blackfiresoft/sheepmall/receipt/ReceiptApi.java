package com.blackfiresoft.sheepmall.receipt;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Api/receipt")
public class ReceiptApi {
    @Resource
    private ReceiptFactory receiptFactory;

    @PostMapping("/create")
    public ResultEntity createReceipt(@RequestBody Receipt receipt) {
        if (receipt == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        receiptFactory.createReceipt(receipt);
        return ResultEntity.success();
    }

    @GetMapping("/delete/{receiptId}")
    public ResultEntity deleteReceipt(@PathVariable Long receiptId) {
        if (receiptId == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        receiptFactory.deleteReceipt(receiptId);
        return ResultEntity.success();
    }

    @GetMapping("/get/{userId}")
    public ResultEntity getByUser(@PathVariable Long userId) {
        if (userId== null||userId<=0) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        List<Receipt> receiptByUser = receiptFactory.getReceiptByUserId(userId);
        if (receiptByUser == null) {
            return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        return ResultEntity.success(receiptByUser);
    }

    @PostMapping("/update/{receiptId}")
    public ResultEntity updateReceipt(@PathVariable Long receiptId, @RequestBody Receipt receipt){
        if (receipt == null || receiptId == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        receiptFactory.updateReceipt(receiptId, receipt);
        return ResultEntity.success();
    }
}
