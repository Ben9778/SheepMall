package com.blackfiresoft.sheepmall.payment;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "payRqs_tb")
public class PayRqs implements Serializable {
    @Serial
    private static final long serialVersionUID = -2794602274624874175L;
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
