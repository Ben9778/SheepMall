package com.blackfiresoft.sheepmall.admin.productHandle;

import com.blackfiresoft.sheepmall.factory.CateGoryFactory;
import com.blackfiresoft.sheepmall.product.CateGory;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/57598")
public class CateGoryApi {
    @Resource
    @Qualifier("categoryImp")
    private CateGoryFactory cateGoryFactory;

    @GetMapping("/delete/{id}")
    public ResultEntity deleteCategory(@PathVariable("id") long id) {
        if (id <= 0) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        cateGoryFactory.deleteCateGoryById(id);
        return ResultEntity.success();
    }

    @PostMapping("/update")
    public ResultEntity updateCategory(@RequestBody CateGory cateGory) {
        cateGoryFactory.updateCateGory(cateGory);
        return ResultEntity.success();
    }

    @PostMapping("/add")
    public ResultEntity addCategory(@RequestBody CateGory cateGory) {
        if (cateGory == null) {
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        cateGoryFactory.addCateGory(cateGory);
        return ResultEntity.success();
    }

    @GetMapping("/all")
    public ResultEntity getAllCategory() {
        List<CateGory> allCateGory = cateGoryFactory.getAllCateGory();
        if (allCateGory != null) {
            return ResultEntity.success(allCateGory);
        }
        return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
    }
}
