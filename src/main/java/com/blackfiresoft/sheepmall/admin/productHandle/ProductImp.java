package com.blackfiresoft.sheepmall.admin.productHandle;

import com.blackfiresoft.sheepmall.product.ProductCommonImp;
import com.blackfiresoft.sheepmall.product.ProductRepository;
import com.blackfiresoft.sheepmall.product.Products;
import com.blackfiresoft.sheepmall.util.GenerateNo;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 管理员操作商品实现类
 */
@Service("productImp")
public class ProductImp extends ProductCommonImp {
    @Resource
    private ProductRepository productRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addProduct(Products product) {
        product.setProductNo(GenerateNo.generate());
        productRepository.saveAndFlush(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteProducts(Long[] ids) {
        for (Long id : ids) {
            productRepository.deleteById(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(Products product) {
        productRepository.findById(product.getId()).ifPresent(p -> {
            p.setProductName(product.getProductName());
            p.setProductSpecs(product.getProductSpecs());
            p.setProductPrice(product.getProductPrice());
            p.setCost(product.getCost());
            p.setProductImgUrl(product.getProductImgUrl());
            p.setImageUrl1(product.getImageUrl1());
            p.setImageUrl2(product.getImageUrl2());
            p.setImageUrl3(product.getImageUrl3());
            p.setStock(product.getStock());
            p.setStatus(product.getStatus());
            p.setProductDesc(product.getProductDesc());
            p.setCategoryName(product.getCategoryName());
            productRepository.saveAndFlush(p);
        });
    }

    @Override
    public Page<Products> getProductByCostRange(BigDecimal minCost, BigDecimal maxCost, int pageNo, int pageSize) {

        return productRepository.findByCostBetween(minCost, maxCost, Pageable.ofSize(pageSize).withPage(pageNo));
    }

    @Override
    public Page<Products> getProductByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, int pageNo, int pageSize) {

        return productRepository.findByProductPriceBetween(minPrice, maxPrice, Pageable.ofSize(pageSize).withPage(pageNo));
    }

    @Override
    public Page<Products> getProductByStockRange(int minStock, int maxStock, int pageNo, int pageSize) {

        return productRepository.findByStockBetween(minStock, maxStock, Pageable.ofSize(pageSize).withPage(pageNo));
    }

    @Override
    public Products getProductByName(String productName) {
        return productRepository.findByProductName(productName);
    }

    @Override
    public Products getProductByProductNo(String productNo) {
        return productRepository.findByProductNo(productNo);
    }

    @Override
    public Page<Products> getAllProducts(int pageNo, int pageSize) {
        return productRepository.findAll(Pageable.ofSize(pageSize).withPage(pageNo));
    }
}
