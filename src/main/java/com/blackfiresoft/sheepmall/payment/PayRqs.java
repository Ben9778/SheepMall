package com.blackfiresoft.sheepmall.payment;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "payRqs_tb")
public class PayRqs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String appid;
    @Column(nullable = false)
    private String merchantId;
    @Column(nullable = false)
    private String privateKeyPath;
    @Column(nullable = false)
    private String merchantSerialNumber;
    @Column(nullable = false)
    private String apiV3Key;
    @Column(nullable = false)
    private String notifyUrl;
    @Column(nullable = false)
    private String refundNotifyUrl;
}
