package com.blackfiresoft.sheepmall.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 6839112462645746693L;
    private Long id;
    private String username;
    private String email;
    private String profile_pic;
    private Timestamp created_at;
}
