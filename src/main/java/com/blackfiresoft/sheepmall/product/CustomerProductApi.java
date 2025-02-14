package com.blackfiresoft.sheepmall.product;

import com.blackfiresoft.sheepmall.dto.ProductDto;
import com.blackfiresoft.sheepmall.factory.ProductFactory;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import com.blackfiresoft.sheepmall.util.DataTransfer;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户商品操作接口类
 */
@RestController
@RequestMapping("/Api/product")
public class CustomerProductApi {

    @Resource(name = "customerProductImp")
    private ProductFactory productFactory;

    @GetMapping("/byPriceDesc")
    public ResultEntity getByPriceDesc(@RequestParam(value = "categoryName") String categoryName,
                                       @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        if (categoryName == null || categoryName.isEmpty()) {
            ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        Page<Products> productsPage = productFactory.getBySortForDesc(categoryName, pageNo, pageSize);
        return ResultEntity.success(productToProductDto(productsPage));
    }

    @GetMapping("/byPriceAsc")
    public ResultEntity getByPriceAsc(@RequestParam(value = "categoryName") String categoryName,
                                      @RequestParam(value = "pageNo",  defaultValue = "0") int pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        if (categoryName == null || categoryName.isEmpty()) {
            ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        Page<Products> productsPage = productFactory.getBySortForAsc(categoryName, pageNo, pageSize);
        return ResultEntity.success(productToProductDto(productsPage));
    }

    @GetMapping("/byCategoryName")
    public ResultEntity getProductByCategoryName(@RequestParam(value = "categoryName") String categoryName,
                                                 @RequestParam(value = "pageNo",  defaultValue = "0") int pageNo,
                                                 @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {

        if (categoryName == null || categoryName.isEmpty()) {
            ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        Page<Products> productsPage = productFactory.getProductByCategoryName(categoryName, pageNo, pageSize);
        return ResultEntity.success(productToProductDto(productsPage));
    }

    @GetMapping("/byStatus")
    public ResultEntity getProductByStatus(@RequestParam(value = "status", defaultValue = "上架") String status,
                                           @RequestParam(value = "pageNo",  defaultValue = "0") int pageNo,
                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        if (status == null || status.isEmpty()) {
            ResultEntity.fail(ResultEnum.PARAM_ERROR);
        }
        Page<Products> productsPage = productFactory.getByStatus(status, pageNo, pageSize);
        return ResultEntity.success(productToProductDto(productsPage));
    }

    /**
     * 将产品对象转换为产品数据传输对象
     *
     * @param productsPage 产品对象列表
     * @return List<ProductDto> 产品数据传输对象列表
     */
    private List<ProductDto> productToProductDto(Page<Products> productsPage) {
        List<ProductDto> productListDto = new ArrayList<>();
        for (Products product : productsPage.getContent()) {
            productListDto.add(DataTransfer.transfer(new ProductDto(), product));
        }
        Page<ProductDto> productPageDto = new PageImpl<>(productListDto, productsPage.getPageable(), productsPage.getTotalElements());
        return productPageDto.getContent();
    }
}

