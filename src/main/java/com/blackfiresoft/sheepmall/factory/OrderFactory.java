package com.blackfiresoft.sheepmall.factory;

import com.blackfiresoft.sheepmall.order.Orders;
import com.blackfiresoft.sheepmall.user.Users;
import org.springframework.data.domain.Page;
import java.util.List;

public interface OrderFactory {

    default List<Orders> getOrdersByUser(Users user) {
        return null;
    }

    default List<Orders> getOrdersByUsername(String username) {
        return null;
    }

    default List<Orders> getOrdersByUserAndStatus(Users user, String status) {
        return null;
    }

    default void deleteOrders(Long[] orderIds) {
    }

    default void createOrder(Orders order) {
    }

    default Page<Orders> getOrdersByStatus(String status, int pageNo, int pageSize) {
        return null;
    }

    default void updateOrderStatus(Long orderId, String status) {
    }

    default void updateOrderStatus(Long orderId,int type) {
    }

    default Orders getOrderByOrderNo(String orderNumber) {
        return null;
    }

    default Page<Orders> getAllOrders(int pageNo, int pageSize) {
        return null;
    }

    default Page<Orders> getOrdersByDateRange(String startDate, String endDate, int pageNo, int pageSize) {
        return null;
    }

    default void updateOrder(Long orderId, Orders order) {
    }

}
