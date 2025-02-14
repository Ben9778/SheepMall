package com.blackfiresoft.sheepmall.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    Products findByProductNo(String productNo);

    Page<Products> findByStatus(String status, Pageable pageable);

    Page<Products> findByCostBetween(BigDecimal minCost, BigDecimal maxCost, Pageable pageable);

    Page<Products> findByProductPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<Products> findByStockBetween(int minStock, int maxStock, Pageable pageable);

    Products findByProductName(String productName);

    Page<Products> findByCategoryName(String cateGoryName, Pageable pageable);

    Page<Products> findByCategoryNameOrderByProductPriceDesc(String categoryName, Pageable pageable);

    Page<Products> findByCategoryNameOrderByProductPriceAsc(String categoryName, Pageable pageable);

}
