package com.blackfiresoft.sheepmall.product;
import com.blackfiresoft.sheepmall.dto.superClass.ProductSuper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product_tb")
@Data
public class Products extends ProductSuper implements Serializable{
    @Serial
    private static final long serialVersionUID = -7961016323187833282L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    @Column(nullable = false)
    private BigDecimal cost;
    @Column(nullable = false)
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private String status;

}
