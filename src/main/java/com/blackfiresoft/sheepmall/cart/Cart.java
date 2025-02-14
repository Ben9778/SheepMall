package com.blackfiresoft.sheepmall.cart;

import com.blackfiresoft.sheepmall.dto.superClass.ProductSuper;
import com.blackfiresoft.sheepmall.user.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "cart_tb")
public class Cart extends ProductSuper implements Serializable {

    @Serial
    private static final long serialVersionUID = -6375538514771743215L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Users users;

}
