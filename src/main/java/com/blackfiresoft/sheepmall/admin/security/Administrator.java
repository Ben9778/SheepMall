package com.blackfiresoft.sheepmall.admin.security;
import jakarta.persistence.*;
import lombok.Data;


import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "admin_tb")
@Data
public class Administrator implements Serializable {

    @Serial
    private static final long serialVersionUID = -2932846740527611145L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    private Integer isInit;
    private Timestamp created_at;

    @PrePersist
    protected void onCreate() {
        created_at = new Timestamp(System.currentTimeMillis());
    }

    public Administrator() {
        this.isInit = 0;
    }
}
