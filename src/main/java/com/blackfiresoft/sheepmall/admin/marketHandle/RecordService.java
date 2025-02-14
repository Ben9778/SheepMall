package com.blackfiresoft.sheepmall.admin.marketHandle;

import com.blackfiresoft.sheepmall.user.Users;

import java.util.List;

public interface RecordService {

    List<Records> getByActivityId(Long activityId);

    List<Records> getByProductNo(String productNo);

    List<Records> getByUsers(Users user);

    void create(Records record);
}
