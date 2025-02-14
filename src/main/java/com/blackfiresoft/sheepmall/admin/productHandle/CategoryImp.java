package com.blackfiresoft.sheepmall.admin.productHandle;

import com.blackfiresoft.sheepmall.factory.CateGoryFactory;
import com.blackfiresoft.sheepmall.product.CateGory;
import com.blackfiresoft.sheepmall.product.CateGoryRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("categoryImp")
public class CategoryImp implements CateGoryFactory {
    @Resource
    private CateGoryRepository cateGoryRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCateGory(CateGory cateGory) {
        cateGoryRepository.saveAndFlush(cateGory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCateGoryById(Long id) {
        cateGoryRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCateGory(CateGory cateGory) {
        cateGoryRepository.findById(cateGory.getId()).ifPresent(c -> {
            c.setName(cateGory.getName());
            cateGoryRepository.saveAndFlush(c);
        });
    }

    @Override
    public List<CateGory> getAllCateGory() {
        return cateGoryRepository.findAll();
    }

}
