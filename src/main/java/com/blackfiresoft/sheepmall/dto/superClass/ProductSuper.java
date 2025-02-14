package com.blackfiresoft.sheepmall.dto.superClass;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@MappedSuperclass
public class ProductSuper implements Serializable {
    @Serial
    private static final long serialVersionUID = 8756520267514106722L;
    private String productNo;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private String productImgUrl;
    @Column(nullable = false)
    private String productDesc;
    @Column(nullable = false)
    private String productSpecs;
    @Column(nullable = false)
    private BigDecimal productPrice;
}
