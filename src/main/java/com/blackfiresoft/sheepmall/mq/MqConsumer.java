package com.blackfiresoft.sheepmall.mq;

import com.blackfiresoft.sheepmall.admin.marketHandle.RecordService;
import com.blackfiresoft.sheepmall.cart.CartService;
import com.blackfiresoft.sheepmall.dto.ActivityDto;
import com.blackfiresoft.sheepmall.factory.ActivityFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 收到通知开始异步处理抢购活动业务
 */
@Component
@Slf4j
public class MqConsumer {
    @Resource
    private CartService cartService;
    @Resource
    private RecordService recordService;
    @Resource(name = "activityImp")
    private ActivityFactory activityFactory;

    @JmsListener(destination = "market")
    public void consume(ActivityDto activityDto) {
        RushThreadPool rushThreadPool = new RushThreadPool();
        RushSaleHandle rushSaleHandle = new RushSaleHandle(activityDto, cartService, recordService,activityFactory);
        rushThreadPool.execute(rushSaleHandle);
    }
}
