package com.blackfiresoft.sheepmall.dto;

import com.blackfiresoft.sheepmall.dto.superClass.ProductSuper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;


/**
 * 封装商品数据对象传给前端
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDto extends ProductSuper implements Serializable {
    @Serial
    private static final long serialVersionUID = 3179847710580195153L;

    private Long id;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private Integer stock;
}
