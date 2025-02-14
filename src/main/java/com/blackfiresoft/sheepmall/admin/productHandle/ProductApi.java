package com.blackfiresoft.sheepmall.admin.productHandle;

import com.blackfiresoft.sheepmall.factory.ProductFactory;
import com.blackfiresoft.sheepmall.product.Products;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/57597")
public class ProductApi {
    @Resource(name = "productImp")
    private ProductFactory productFactory;

    @PostMapping("/add")
    public ResultEntity addProduct(@RequestBody Products product){
        if (product == null){
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        productFactory.addProduct(product);
        return ResultEntity.success();
    }

    @GetMapping("/all/{pageNo}/{pageSize}")
    public ResultEntity allProduct(@PathVariable int pageNo, @PathVariable int pageSize){
        Page<Products> allProducts = productFactory.getAllProducts(pageNo, pageSize);
        if (allProducts == null){
            return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        return ResultEntity.success(allProducts.getContent());
    }

    @PostMapping("/delete")
    public ResultEntity deleteProduct(@RequestBody Long[]ids){
        if (ids == null || ids.length < 1){
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        productFactory.batchDeleteProducts(ids);
        return ResultEntity.success();
    }

    @PostMapping("/update")
    public ResultEntity updateProduct(@RequestBody Products product){
        if (product == null){
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        productFactory.updateProduct(product);
        return ResultEntity.success();
    }

    @GetMapping("/byProductNo")
    public ResultEntity getProductByNo(@RequestParam(value = "productNo") String productNo){
        if (productNo == null || productNo.trim().isEmpty()){
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return ResultEntity.success(productFactory.getProductByProductNo(productNo));
    }

    @PostMapping("/byCost")
    public ResultEntity getProductByCostRange(@RequestParam(value = "minCost") BigDecimal minCost, @RequestParam(value = "maxCost") BigDecimal maxCost, int pageNo, int pageSize){
        if (minCost == null || maxCost == null || minCost.compareTo(maxCost) >= 0){
            return ResultEntity.fail(ResultEnum.INVALID_RANGE);
        }
        return ResultEntity.success(productFactory.getProductByCostRange(minCost, maxCost, pageNo, pageSize).getContent());
    }

    @GetMapping("/byPrice")
    public ResultEntity getProductByPriceRange(BigDecimal minPrice, BigDecimal maxPrice,int pageNo, int pageSize){
        if (minPrice == null || maxPrice == null || minPrice.compareTo(maxPrice) >= 0){
            return ResultEntity.fail(ResultEnum.INVALID_RANGE);
        }
        return ResultEntity.success(productFactory.getProductByPriceRange(minPrice, maxPrice, pageNo, pageSize).getContent());
    }

    @GetMapping("/byStock")
    public ResultEntity getProductByStockRange(int minStock, int maxStock,int pageNo, int pageSize){
        if (minStock > maxStock){
            return ResultEntity.fail(ResultEnum.INVALID_RANGE);
        }
        return ResultEntity.success(productFactory.getProductByStockRange(minStock, maxStock, pageNo, pageSize).getContent());
    }

    @GetMapping("/byProductName")
    public ResultEntity getProductByProductName(@RequestParam(value = "productName") String productName) {
        if (productName == null || productName.trim().isEmpty()){
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return ResultEntity.success(productFactory.getProductByName(productName));
    }

    @GetMapping("/byCname")
    public ResultEntity getProductByCategoryName(@RequestParam(value = "CategoryName") String CategoryName,
                                              @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
                                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        if (CategoryName == null || CategoryName.trim().isEmpty()){
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return ResultEntity.success(productFactory.getProductByCategoryName(CategoryName, pageNo, pageSize).getContent());
    }

    @GetMapping("/byStatus")
    public ResultEntity getProductByStatus(@RequestParam(value = "status") String status,
                                                 @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        if (status == null || status.trim().isEmpty()){
            return ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        return ResultEntity.success(productFactory.getByStatus(status, pageNo, pageSize).getContent());
    }
}
