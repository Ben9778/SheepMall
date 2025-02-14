package com.blackfiresoft.sheepmall.product;

import com.blackfiresoft.sheepmall.factory.ProductFactory;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


/**
 * 商品操作公共使用实现类
 */
public class ProductCommonImp implements ProductFactory {

    @Resource
    private ProductRepository productRepository;

    //根据商品分类名称查询商品
    @Override
    public Page<Products> getProductByCategoryName(String CategoryName,int pageNo, int pageSize){
        return productRepository.findByCategoryName(CategoryName, PageRequest.of(pageNo, pageSize));
    }

    //根据商品状态查询商品,客户端默认状态是'已上架',管理员需根据需求查询上架或下架商品
    @Override
    public Page<Products> getByStatus(String status, int pageNo, int pageSize) {
        return productRepository.findByStatus(status, PageRequest.of(pageNo,pageSize));
    }

}
