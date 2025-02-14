package com.blackfiresoft.sheepmall.market;

import com.blackfiresoft.sheepmall.dto.ActivityDto;
import com.blackfiresoft.sheepmall.mq.MqProducer;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 商品抢购活动服务实现类
 */
@Service("customerActivityImp")
public class CustomerActivityImp extends ActivityCommonImp {
    @Resource
    private ActivityRepository activityRepository;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private MqProducer producer;

    /**
     * 秒杀商品入口执行
     *
     * @param activityDto 接收参数:用户id,活动商品编码,价格,数量
     * @return ResultEntity.success() 成功
     */
    @Override
    public ResultEntity rushSale(ActivityDto activityDto) {

        String lockKey = "lock:rush:" + activityDto.getActivityId();
        // 获取锁的唯一标识
        String requestId = UUID.randomUUID().toString();
        // 尝试加锁
        boolean isLocked = acquireLock(lockKey, requestId);
        if (!isLocked) {
            return ResultEntity.fail(ResultEnum.LOGIN_FAILED);
        }
        String user_key = "user_" + activityDto.getActivityId();
        String activity_key=activityDto.getProductNo()+"-"+activityDto.getActivityId();
        try {
            //校验是否在秒杀时间段内
            Optional<Activity> activity = activityRepository.findById(activityDto.getActivityId());
            if(activity.isEmpty()){
                return ResultEntity.fail(ResultEnum.ACTIVITY_NOT_FOUND);
            }
            if (activity.get().getEndDate().before(new Timestamp(System.currentTimeMillis()))) {
                return ResultEntity.fail(ResultEnum.ACTIVITY_EXPIRED);
            }else if (activity.get().getBeginDate().after(new Timestamp(System.currentTimeMillis()))) {
                return ResultEntity.fail(ResultEnum.ACTIVITY_NOT_START);
            }
            // 校验库存是否充足
            Boolean isExit = redisTemplate.hasKey(activity_key);
            List<Object> productList = redisTemplate.opsForList().range(activity_key, 0, -1);
            if (Boolean.FALSE.equals(isExit) || Objects.requireNonNull(productList).isEmpty()) {
                return ResultEntity.fail(ResultEnum.STOCK_NOT_ENOUGH);
            }
            // 校验用户是否已购买
            Boolean isBuy = redisTemplate.opsForSet().isMember(user_key, activityDto.getUsers().getId());
            if (Boolean.TRUE.equals(isBuy)) {
                return ResultEntity.fail(ResultEnum.USER_HAS_BUY);
            }
            // 扣减库存
            redisTemplate.opsForList().rightPop(activity_key);
            // 记录用户购买记录
            redisTemplate.opsForSet().add(user_key, activityDto.getUsers().getId());
            // 发送消息通知mq处理订单
            producer.sendMessage(activityDto);
            return ResultEntity.success();
        } finally {
            releaseLock(lockKey, requestId);
        }
    }

    /**
     * 获取锁
     *
     * @param lockKey   锁的key
     * @param requestId 锁的唯一标识
     * @return true 成功 false 失败
     */
    private boolean acquireLock(String lockKey, String requestId) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, requestId);
        if (Boolean.TRUE.equals(success)) {
            // 设置锁的过期时间
            redisTemplate.expire(lockKey, 10, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param lockKey   锁的key
     * @param requestId 锁的唯一标识
     */
    private void releaseLock(String lockKey, String requestId) {
        String storedRequestId = (String) redisTemplate.opsForValue().get(lockKey);
        if (storedRequestId != null && storedRequestId.equals(requestId)) {
            redisTemplate.delete(lockKey);
        }
    }
}
