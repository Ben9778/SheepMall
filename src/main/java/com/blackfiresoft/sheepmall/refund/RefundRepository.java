package com.blackfiresoft.sheepmall.refund;

import com.blackfiresoft.sheepmall.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RefundRepository extends JpaRepository<Refund, Long> {

    Refund findByRefundNo(String refundNo);

    Refund findByOrderNo(String orderNo);

    List<Refund> findByUsers(Users user);

    List<Refund> findByStatus(String status);

    @Query(value = "SELECT R.* FROM refund_tb R WHERE date(R.created_at)=curdate()", nativeQuery = true)
    List<Refund> findByToday();

    @Query(value = "SELECT R.* FROM refund_tb R WHERE curdate()-date(R.created_at)=1", nativeQuery = true)
    List<Refund> findByYesterday();

}
