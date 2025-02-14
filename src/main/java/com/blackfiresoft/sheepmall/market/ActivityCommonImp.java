package com.blackfiresoft.sheepmall.market;

import com.blackfiresoft.sheepmall.factory.ActivityFactory;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * 活动共用接口实现类
 */
public class ActivityCommonImp implements ActivityFactory {
    @Resource
    private ActivityRepository activityRepository;

    @Override
    public List<Activity> getByStatus(String status) {
        return activityRepository.findByStatus(status);
    }
}
