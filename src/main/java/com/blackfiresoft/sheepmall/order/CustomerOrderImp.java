package com.blackfiresoft.sheepmall.order;

import com.blackfiresoft.sheepmall.factory.OrderFactory;
import com.blackfiresoft.sheepmall.user.Users;
import com.blackfiresoft.sheepmall.util.StatusEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("customerOrderImp")
public class CustomerOrderImp implements OrderFactory {

    @Resource
    private OrderRepository orderRepository;

    @Override
    public List<Orders> getOrdersByUser(Users user) {
        return orderRepository.findByUsers(user);
    }

    @Override
    public List<Orders> getOrdersByUserAndStatus(Users user, String status) {
        return orderRepository.findByUsersAndStatus(user, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrders(Long[] orderIds) {
        for (Long orderId : orderIds) {
            orderRepository.deleteById(orderId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(Orders order) {
        UUID uuid = UUID.randomUUID();
        order.setOrderNo(uuid.toString().replace("-", ""));
        orderRepository.saveAndFlush(order);
    }

    /**
     * 判断客户订单状态
     * @param orderId 订单id
     * @param type 0:订单取消 1:退款中 2:取消退款后改为已完成状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(Long orderId, int type) {
        orderRepository.findById(orderId).ifPresent(order -> {
            if (type == 0) {
                order.setStatus(StatusEnum.CANCELLED.toString());
                orderRepository.saveAndFlush(order);
            } else if (type == 1) {
                order.setStatus(StatusEnum.REFUNDING.toString());
                orderRepository.saveAndFlush(order);
            } else {
                order.setStatus(StatusEnum.COMPLETED.toString());
                orderRepository.saveAndFlush(order);
            }
        });
    }
}
