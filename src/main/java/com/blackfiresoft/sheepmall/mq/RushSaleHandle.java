package com.blackfiresoft.sheepmall.mq;

import com.blackfiresoft.sheepmall.admin.marketHandle.*;
import com.blackfiresoft.sheepmall.cart.Cart;
import com.blackfiresoft.sheepmall.cart.CartService;
import com.blackfiresoft.sheepmall.dto.ActivityDto;
import com.blackfiresoft.sheepmall.exception.CustomException;
import com.blackfiresoft.sheepmall.factory.ActivityFactory;
import com.blackfiresoft.sheepmall.market.Activity;
import com.blackfiresoft.sheepmall.util.DataTransfer;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 用户抢购之后生成购物车记录和活动记录
 */
@Slf4j
public class RushSaleHandle implements Runnable {

    private final CartService cartService;
    private final RecordService recordService;
    private final ActivityFactory activityFactory;
    private final ActivityDto activityDto;

    public RushSaleHandle(ActivityDto activityDto,CartService cartService,RecordService recordService,ActivityFactory activityFactory) {
        this.activityDto = activityDto;
        this.cartService = cartService;
        this.recordService = recordService;
        this.activityFactory = activityFactory;
    }

    @Override
    public void run() {
        Activity activity = exitActivity(activityDto);
        createRecord(activity);
        createCart(activity);
    }

    /**
     * 创建活动记录
     */
    private void createRecord(Activity activity) {
        Records records = new Records();
        records.setActivityId(activity.getId());
        Object recordTransfer = DataTransfer.transfer(records, activityDto);
        recordService.create((Records) recordTransfer);
        log.info("用户:{}抢购商品:{}成功,生成活动记录", activityDto.getUsers().getId(), activityDto.getProductNo());
    }

    /**
     * 创建购物车
     */
    private void createCart(Activity activity) {
        Cart cart = new Cart();
        cart.setUsers(activityDto.getUsers());
        cart.setQuantity(activityDto.getQuantity());
        cart.setTotalPrice(activityDto.getPrice());
        Object transfer = DataTransfer.transfer(cart, activity);
        cartService.addProductToCart((Cart) transfer);
        log.info("用户:{}抢购商品:{}成功,生成购物车记录", activityDto.getUsers().getId(), activityDto.getProductNo());
    }

    /**
     * 判断活动商品是否存在
     *
     * @return Activity or null
     */
    private Activity exitActivity(ActivityDto activityDto) {
        Optional<Activity> byId = activityFactory.getById(activityDto.getActivityId());
        if (byId.isEmpty()) {
            log.info("活动:{}不存在", activityDto.getActivityId());
            throw new CustomException("the activity is not exist");
        }
        return byId.get();
    }
}
