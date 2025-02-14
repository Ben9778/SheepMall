package com.blackfiresoft.sheepmall.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class RecordDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 5652468847076640290L;
    private Long recordId;
    private String username;
    private String email;
    private String productNo;
    private Integer quantity;
    private Timestamp joinTime;
}
