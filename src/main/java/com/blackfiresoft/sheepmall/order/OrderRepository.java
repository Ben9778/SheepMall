package com.blackfiresoft.sheepmall.order;

import com.blackfiresoft.sheepmall.user.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByUsers(Users user);

    List<Orders> findByUsersAndStatus(Users user, String status);

    Page<Orders> findByStatus(String status, Pageable pageable);

    Orders findByOrderNo(String orderNo);

    Page<Orders> findByCreateTimeBetween(Timestamp startDate, Timestamp endDate, Pageable pageable);

    @Query(value = "SELECT O.* FROM order_tb O WHERE date(O.create_time)=curdate()", nativeQuery = true)
    List<Orders> findByTdSale();

    @Query(value = "SELECT O.* FROM order_tb O WHERE curdate()-date(o.create_time)=1", nativeQuery = true)
    List<Orders> findByYdSale();

    @Query(value = "SELECT date_format(O.create_time,'%m/%d') as saleDay,sum(O.quantity) as saleQuantity FROM order_tb O where DATE_SUB(CURDATE(), INTERVAL(7) DAY) <= date(O.create_time) group by saleDay order by saleDay asc",nativeQuery = true)
    List<Map<String, Object>> findBySevenDaySale();

    @Query(value = "SELECT date_format(O.create_time,'%m/%d') as saleDay,sum(O.quantity) as saleQuantity FROM order_tb O where DATE_SUB(CURDATE(), INTERVAL(30) DAY) <= date(O.create_time) group by saleDay order by saleDay asc", nativeQuery = true)
    List<Map<String, Object>> findByThirtyDaySale();

    @Query(value = "SELECT O.* FROM order_tb O WHERE date(O.create_time)=curdate()", nativeQuery = true)
    List<Orders> findByTdIncome();

    @Query(value = "SELECT O.* FROM order_tb O WHERE curdate()-date(O.create_time)=1", nativeQuery = true)
    List<Orders> findByYdIncome();

    @Query(value = "SELECT date_format(O.create_time,'%m/%d') as incomeDay,sum(O.total_price) as income FROM order_tb O WHERE DATE_SUB(CURDATE(), INTERVAL(7) DAY) <= date(O.create_time) group by incomeDay order by incomeDay asc", nativeQuery = true)
    List<Map<String, Object>> findBySevenDayIncome();

    @Query(value = "SELECT date_format(O.create_time,'%m/%d') as incomeDay,sum(O.total_price) as income FROM order_tb O WHERE DATE_SUB(CURDATE(), INTERVAL(30) DAY) <= date(O.create_time) group by incomeDay order by incomeDay asc", nativeQuery = true)
    List<Map<String, Object>> findByThirtyDayIncome();
}
