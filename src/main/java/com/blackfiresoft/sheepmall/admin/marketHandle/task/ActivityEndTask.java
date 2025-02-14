package com.blackfiresoft.sheepmall.admin.marketHandle.task;
import com.blackfiresoft.sheepmall.admin.marketHandle.ActivityImp;
import lombok.Getter;

/**
 * 活动结束状态管理类
 */
public class ActivityEndTask implements Runnable {

    private final ActivityImp activityImp;
    private final Long activityId;
    private final String productNo;

    public ActivityEndTask(Long activityId, String productNo, ActivityImp activityImp) {
        this.activityImp = activityImp;
        this.activityId = activityId;
        this.productNo = productNo;
    }

    @Override
    public void run() {
        afterHandle();
    }

    /**
     * 执行活动结束的时候修改活动状态为:已结束,同时删除redis相关数据
     */
    private void afterHandle() {
       activityImp.updateActivityStatus(status.ended.value, activityId);
       activityImp.checkAndRemove(activityId, productNo);
    }

    @Getter
    private enum status {
        ended("已结束");

        private final String value;

        status(String value) {
            this.value = value;
        }

    }
}
