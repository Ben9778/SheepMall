package com.blackfiresoft.sheepmall.dto;
import com.blackfiresoft.sheepmall.user.Users;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ActivityDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 5265371681407106140L;

    private Long activityId;
    private String productNo;
    private BigDecimal price;
    private Integer quantity;
    private Users users;
}
