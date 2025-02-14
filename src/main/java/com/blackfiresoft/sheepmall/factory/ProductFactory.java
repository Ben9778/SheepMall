package com.blackfiresoft.sheepmall.factory;

import com.blackfiresoft.sheepmall.product.Products;
import org.springframework.data.domain.Page;


import java.math.BigDecimal;

public interface ProductFactory {

    default void addProduct(Products product) {
    }


    default void batchDeleteProducts(Long[] ids) {
    }


    default void updateProduct(Products product) {
    }


    default Products getProductByProductNo(String productNo) {
        return null;
    }

    default Page<Products> getByStatus(String status, int pageNo, int pageSize){
        return null;
    }


    default Page<Products> getProductByCostRange(BigDecimal minCost, BigDecimal maxCost, int pageNo, int pageSize) {
        return null;
    }


    default Page<Products> getProductByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, int pageNo, int pageSize) {
        return null;
    }


    default Page<Products> getProductByStockRange(int minStock, int maxStock, int pageNo, int pageSize){
        return null;
    }


    default Products getProductByName(String productName){
        return null;
    }

    default Page<Products> getProductByCategoryName(String CategoryName,int pageNo, int pageSize){
        return null;
    }

    default Page<Products> getAllProducts(int pageNo, int pageSize){
        return null;
    }

    default Page<Products> getBySortForDesc(String CateGoryName,int pageNo, int pageSize) {
        return null;
    }

    default Page<Products> getBySortForAsc(String CateGoryName,int pageNo, int pageSize) {
        return null;
    }

}
