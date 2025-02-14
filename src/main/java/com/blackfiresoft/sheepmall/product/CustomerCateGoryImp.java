package com.blackfiresoft.sheepmall.product;


import com.blackfiresoft.sheepmall.factory.CateGoryFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("customerCateGoryImp")
public class CustomerCateGoryImp implements CateGoryFactory {
    @Resource
    private CateGoryRepository cateGoryRepository;

    @Override
    public List<CateGory> getAllCateGory() {
        return cateGoryRepository.findAll();
    }

}
