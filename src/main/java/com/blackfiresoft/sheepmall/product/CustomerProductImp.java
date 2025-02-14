package com.blackfiresoft.sheepmall.product;

import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * 客户端商品操作实现类
 */
@Service("customerProductImp")
public class CustomerProductImp extends ProductCommonImp {

    @Resource
    private ProductRepository productRepository;

    //根据商品分类名称查询并降序排序
    @Override
    public Page<Products> getBySortForDesc(String categoryName, int pageNo, int pageSize) {
        return productRepository.findByCategoryNameOrderByProductPriceDesc(categoryName, PageRequest.of(pageNo, pageSize, Sort.by("productPrice").descending()));
    }

    // 根据商品分类名称查询并升序排序
    @Override
    public Page<Products> getBySortForAsc(String categoryName, int pageNo, int pageSize) {
        return productRepository.findByCategoryNameOrderByProductPriceAsc(categoryName, PageRequest.of(pageNo, pageSize, Sort.by("productPrice").ascending()));
    }
}
