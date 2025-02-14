package com.blackfiresoft.sheepmall.market;

import com.blackfiresoft.sheepmall.dto.superClass.ProductSuper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "activity_tb")
public class Activity extends ProductSuper implements Serializable {
    @Serial
    private static final long serialVersionUID = -6582682218667998398L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Timestamp beginDate;
    @Column(nullable = false)
    private Timestamp endDate;
    private Timestamp created_at;
    private String status;

    @PrePersist
    protected void onCreate() {
        created_at = new Timestamp(System.currentTimeMillis());
    }

    public Activity() {
        this.status = "未开始";
    }
}
