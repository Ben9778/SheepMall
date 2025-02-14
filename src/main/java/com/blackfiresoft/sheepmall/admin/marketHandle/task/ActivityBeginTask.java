package com.blackfiresoft.sheepmall.admin.marketHandle.task;
import com.blackfiresoft.sheepmall.admin.marketHandle.ActivityImp;
import lombok.Getter;


/**
 * 活动开始状态管理类
 */
public class ActivityBeginTask implements Runnable {

    private final ActivityImp activityImp;
    private final Long activityId;

    public ActivityBeginTask(Long activityId, ActivityImp activityImp) {
        this.activityImp = activityImp;
        this.activityId = activityId;
    }

    @Override
    public void run(){
        preHandle();
    }

    /**
     * 执行活动开始的时候修改活动状态为:已开始
     */
    private void preHandle(){
        activityImp.updateActivityStatus(status.started.value, activityId);
    }

    @Getter
    private enum status{
        started("已开始");

        private final String value;
        status(String value){
            this.value = value;
        }

    }
}
