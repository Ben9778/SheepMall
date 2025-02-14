package com.blackfiresoft.sheepmall.factory;

import com.blackfiresoft.sheepmall.dto.ActivityDto;
import com.blackfiresoft.sheepmall.market.Activity;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import java.util.List;
import java.util.Optional;


public interface ActivityFactory {

    default ResultEntity createActivity(Activity activity) {
        return null;
    }

    default ResultEntity deleteActivity(Long activityId, String productNo) {
        return null;
    }

    default void updateActivityStatus(String activityStatus, Long activityId) {
    }

    default List<Activity> getAllActivity() {
        return null;
    }

    default Optional<Activity> getById(Long activityId) {
        return Optional.empty();
    }

    default List<Activity> getByProductNo(String productNo) {
        return null;
    }

    default List<Activity> getByStatus(String status) {
        return null;
    }

    default void checkAndRemove(Long activityId, String productNo) {
    }

    default ResultEntity rushSale(ActivityDto activityDto) {
        return null;
    }
}
