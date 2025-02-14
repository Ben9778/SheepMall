package com.blackfiresoft.sheepmall.refund;

import com.blackfiresoft.sheepmall.user.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "refund_tb")
public class Refund implements Serializable {
    @Serial
    private static final long serialVersionUID = 7752385820030916160L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String refundNo;
    @Column(nullable = false)
    private String orderNo;
    @Column(nullable = false)
    private String telephone;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private String productImg;
    @Column(nullable = false)
    private BigDecimal productPrice;
    @Column(nullable = false)
    private BigDecimal refundAmount;
    @Column(nullable = false)
    private String reason;
    @Column(nullable = false)
    private String description;
    private String image;
    private String status;
    @Column(updatable = false)
    private Timestamp created_at;


    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Users users;

    @PrePersist
    protected void onCreate() {
        created_at = new Timestamp(System.currentTimeMillis());
    }

    public Refund(){
        this.setStatus("待审核");
    }
}
