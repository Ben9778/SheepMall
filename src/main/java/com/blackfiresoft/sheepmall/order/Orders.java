package com.blackfiresoft.sheepmall.order;

import com.blackfiresoft.sheepmall.dto.superClass.ProductSuper;
import com.blackfiresoft.sheepmall.user.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;



@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "order_tb")
public class Orders extends ProductSuper implements Serializable {
    @Serial
    private static final long serialVersionUID = 7445797344362439165L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String orderNo;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private BigDecimal totalPrice;
    private String payWay;
    private String status;
    private String payment_at;
    @Column(updatable = false)
    private Timestamp createTime;
    private Timestamp updated_at;
    @Column(nullable = false)
    private String recipient;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String area;
    @Column(nullable = false)
    private String detailedAddress;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Users users;

    @PrePersist
    protected void onCreate() {
        createTime = new Timestamp(System.currentTimeMillis());
        updated_at = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = new Timestamp(System.currentTimeMillis());
    }

    public Orders() {
        this.status = "待支付";
        this.payWay = "wechat_Pay";
    }
}
