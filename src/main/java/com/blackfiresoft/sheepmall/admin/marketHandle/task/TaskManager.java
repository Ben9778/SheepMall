package com.blackfiresoft.sheepmall.admin.marketHandle.task;
import com.blackfiresoft.sheepmall.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 活动定时任务调度管理类
 */
@Component
@Slf4j
public class TaskManager {
   private final ScheduledExecutorService scheduledExecutorService;
   private final Map<String,ScheduledFuture<?>> taskMap;

   public TaskManager() {
       taskMap = new ConcurrentHashMap<>();
       scheduledExecutorService= Executors.newScheduledThreadPool(10);
   }

    /**
     * 添加定时任务
     * @param taskName 任务名
     * @param task 任务具体实现
     * @param delay 延迟时间
     * @param timeUnit 时间单位
     */
   public void addTask(String taskName,Runnable task,long delay, TimeUnit timeUnit) {
       if(taskMap.containsKey(taskName)) {
           log.error("定时任务:{}已经存在",taskName);
           throw new CustomException("task already exist");
       }
       ScheduledFuture<?> scheduledFuture = scheduledExecutorService.schedule(task,delay,timeUnit);
       taskMap.put(taskName,scheduledFuture);
       log.info("添加任务:{}成功",taskName);
   }

    /**
     * 删除停止定时任务
     * @param taskName 任务名
     */
   public void removeTask(String taskName) {
       ScheduledFuture<?> scheduledFuture = taskMap.get(taskName);
       if(scheduledFuture != null) {
           scheduledFuture.cancel(true);
           taskMap.remove(taskName);
           log.info("任务已停止并删除:{}", taskName);
       }else {
           log.error("找不到定时任务:{}", taskName);
           throw new CustomException("task not found");
       }
   }

    /**
     * 强制关闭调度器
     */
   public void shutdown() {
       scheduledExecutorService.shutdown();
       log.info("定时调度器已关闭");
   }
}