package com.blackfiresoft.sheepmall.product;

import com.blackfiresoft.sheepmall.factory.CateGoryFactory;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Api/category")
public class CustomerCateGoryApi {
    @Resource
    @Qualifier("customerCateGoryImp")
    private CateGoryFactory cateGoryFactory;

    @GetMapping("/all")
    public ResultEntity getAllCategory() {
        List<CateGory> allCateGory = cateGoryFactory.getAllCateGory();
        if (allCateGory != null) {
            return ResultEntity.success(allCateGory);
        }
        return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
    }
}
