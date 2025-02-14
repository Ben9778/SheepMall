package com.blackfiresoft.sheepmall.user;
import com.blackfiresoft.sheepmall.cart.Cart;
import com.blackfiresoft.sheepmall.admin.marketHandle.Records;
import com.blackfiresoft.sheepmall.order.Orders;
import com.blackfiresoft.sheepmall.receipt.Receipt;
import com.blackfiresoft.sheepmall.refund.Refund;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "user_tb")
public class Users implements Serializable {
    @Serial
    private static final long serialVersionUID = 2368060437708087535L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String profile_pic;
    @Column(updatable = false)
    private Timestamp created_at;
    private Timestamp updated_at;

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JsonManagedReference
    private List<Receipt> receiptList;

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JsonManagedReference
    private List<Cart> cartList;

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JsonManagedReference
    private List<Orders> orderList;

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JsonManagedReference
    private List<Refund> refundList;

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JsonManagedReference
    private List<Records> recordsList;

    @PrePersist
    protected void onCreate() {
        created_at = new Timestamp(System.currentTimeMillis());
        updated_at = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = new Timestamp(System.currentTimeMillis());
    }
}
