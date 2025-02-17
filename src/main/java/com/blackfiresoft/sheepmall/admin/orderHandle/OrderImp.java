package com.blackfiresoft.sheepmall.admin.orderHandle;

import com.blackfiresoft.sheepmall.order.Orders;
import com.blackfiresoft.sheepmall.order.OrderRepository;
import com.blackfiresoft.sheepmall.factory.OrderFactory;
import com.blackfiresoft.sheepmall.user.UserRepository;
import com.blackfiresoft.sheepmall.user.Users;
import com.blackfiresoft.sheepmall.util.TimeTransfer;
import jakarta.annotation.Resource;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;


@Service("orderImp")
public class OrderImp implements OrderFactory {

    @Resource
    private OrderRepository orderRepository;
    @Resource
    private UserRepository userRepository;

    @Override
    public Page<Orders> getOrdersByStatus(String status, int pageNo, int pageSize){
       return orderRepository.findByStatus(status, PageRequest.of(pageNo, pageSize));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Orders> getOrdersByUsername(String username){
        Users byUsername = userRepository.findByUsername(username);
        Hibernate.initialize(byUsername.getOrderList());
        return byUsername.getOrderList();
    }

    @Override
    public void updateOrderStatus(Long orderId, String status){
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.saveAndFlush(order);
        });
    }

    @Override
    public void updateOrderStatus(String orderNo, String status){
       Orders order=orderRepository.findByOrderNo(orderNo);
        if(order!=null){
            order.setStatus(status);
            orderRepository.saveAndFlush(order);
        }
    }

    @Override
    public Orders getOrderByOrderNo(String orderNo){
        return orderRepository.findByOrderNo(orderNo);
    }

    @Override
    public Page<Orders> getAllOrders(int pageNo, int pageSize){
        return orderRepository.findAll(PageRequest.of(pageNo, pageSize));
    }

    @Override
    public Page<Orders> getOrdersByDateRange(String startDate, String endDate, int pageNo, int pageSize){
        Timestamp startTime = TimeTransfer.StringToTimestamp(startDate);
        Timestamp endTime = TimeTransfer.StringToTimestamp(endDate);
        return orderRepository.findByCreateTimeBetween(startTime, endTime, PageRequest.of(pageNo, pageSize));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrder(Long orderId, Orders order){
        orderRepository.findById(orderId).ifPresent(o -> {
            o.setRecipient(order.getRecipient());
            o.setPhoneNumber(order.getPhoneNumber());
            o.setArea(order.getArea());
            o.setDetailedAddress(order.getDetailedAddress());
            orderRepository.saveAndFlush(o);
        });
    }
}
