package com.blackfiresoft.sheepmall.admin.marketHandle;

import com.blackfiresoft.sheepmall.user.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 用户参与抢购活动记录表
 */
@Entity
@Data
@Table(name = "record_tb")
public class Records {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long activityId;
    @Column(nullable = false)
    private String productNo;
    @Column(nullable = false)
    private Integer quantity;
    @Column(updatable = false)
    private Timestamp joinTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Users users;

    @PrePersist
    protected void onCreate() {
        joinTime = new Timestamp(System.currentTimeMillis());
    }
}
