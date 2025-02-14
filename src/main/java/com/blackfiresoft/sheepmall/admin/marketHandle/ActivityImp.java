package com.blackfiresoft.sheepmall.admin.marketHandle;

import com.blackfiresoft.sheepmall.admin.marketHandle.task.ActivityBeginTask;
import com.blackfiresoft.sheepmall.admin.marketHandle.task.ActivityEndTask;
import com.blackfiresoft.sheepmall.admin.marketHandle.task.TaskManager;
import com.blackfiresoft.sheepmall.market.Activity;
import com.blackfiresoft.sheepmall.market.ActivityCommonImp;
import com.blackfiresoft.sheepmall.market.ActivityRepository;
import com.blackfiresoft.sheepmall.product.ProductRepository;
import com.blackfiresoft.sheepmall.product.Products;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 管理员操作活动实现类
 */
@Service("activityImp")
public class ActivityImp extends ActivityCommonImp {
    @Resource
    private ActivityRepository activityRepository;
    @Resource
    private ProductRepository productRepository;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private TaskManager taskManager;
    private final TimeUnit timeUnit = TimeUnit.SECONDS;

    /**
     * 管理员创建活动,同时创建redis对应商品队列
     *
     * @param activity 活动对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultEntity createActivity(Activity activity) {
        int quantity = activity.getQuantity();
        String productNo = activity.getProductNo();
        Timestamp startTime = activity.getBeginDate();
        Timestamp endTime = activity.getEndDate();
        long beginTime = (startTime.getTime()-System.currentTimeMillis()) / 1000;
        long shutTime = (endTime.getTime()-System.currentTimeMillis()) / 1000;
        //活动商品数量不能大于商品库存
        Products productByProductNo = productRepository.findByProductNo(activity.getProductNo());
        if(productByProductNo.getStock() < activity.getQuantity()){
            return ResultEntity.fail(ResultEnum.QUANTITY_NOT_ENOUGH);
        }
        //活动时间判断:开始时间不能大于结束时间,开始时间不能小于当前时间
        if (startTime.after(endTime)||startTime.equals(endTime)||startTime.before(new Timestamp(System.currentTimeMillis()))) {
            return ResultEntity.fail(ResultEnum.ACTIVITY_TIME_ERROR);
        }
        //创建活动,活动入库
        Activity result = activityRepository.saveAndFlush(activity);
        //将活动的商品加入redis队列
        for (int i = 0; i < quantity; i++) {
            redisTemplate.opsForList().leftPush(productNo + "-" + result.getId(), productNo + "_" + i);
        }
        //开启活动任务状态监控
        String beginTaskName = "activity-" + activity.getId() + "-beginTask";
        String endTaskName = "activity-" + activity.getId() + "-endTask";
        taskManager.addTask(beginTaskName, new ActivityBeginTask(result.getId(),this), beginTime, timeUnit);
        taskManager.addTask(endTaskName, new ActivityEndTask(result.getId(), productNo,this), shutTime, timeUnit);
        return ResultEntity.success();
    }

    /**
     * 管理员删除活动,同时删除redis对应商品队列和用户记录
     *
     * @param activityId 活动id
     * @param productNo  商品编码用于对应redis队列key
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultEntity deleteActivity(Long activityId, String productNo) {
        activityRepository.findById(activityId).ifPresent(activity -> {
            Timestamp startTime = activity.getBeginDate();
            Timestamp endTime = activity.getEndDate();
            Timestamp nowTime = new Timestamp(System.currentTimeMillis());
            if (nowTime.after(startTime) && nowTime.before(endTime)) {
                ResultEntity.fail(ResultEnum.REMOVE_ACTIVITY_FAILED);
            }
        });
        activityRepository.deleteById(activityId);
        checkAndRemove(activityId, productNo);
        taskManager.removeTask("activity-" + activityId + "-beginTask");
        taskManager.removeTask("activity-" + activityId + "-endTask");
        return ResultEntity.success();
    }

    /**
     * 修改状态用于活动开始和结束以及删除活动对应的状态改变
     *
     * @param status:未开始/进行中/已结束
     * @param activityId         活动Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateActivityStatus(String status, Long activityId) {
        activityRepository.findById(activityId).ifPresent(a -> {
            a.setStatus(status);
            activityRepository.saveAndFlush(a);
        });
    }

    /**
     * 查看所有活动
     *
     * @return List<Activity>
     */
    @Override
    public List<Activity> getAllActivity() {
        return activityRepository.findAll();
    }

    /**
     * 根据商品编码查询活动
     *
     * @param productNo 商品编码
     * @return List<Activity> 商品对应的活动列表
     */
    @Override
    public List<Activity> getByProductNo(String productNo) {
        return activityRepository.findByProductNo(productNo);
    }

    @Override
    public Optional<Activity> getById(Long activityId) {
        return activityRepository.findById(activityId);
    }

    /**
     * 检查redis数据并删除
     */
    @Override
    public void checkAndRemove(Long activityId, String productNo) {
        Boolean isExit = redisTemplate.hasKey(productNo + "-" + activityId);
        Boolean isBuy = redisTemplate.hasKey("user_" + activityId);
        if (Boolean.TRUE.equals(isExit)) {
            redisTemplate.delete(productNo + "-" + activityId);
        }
        if (Boolean.TRUE.equals(isBuy)) {
            redisTemplate.delete("user_" + activityId);
        }
    }
}
