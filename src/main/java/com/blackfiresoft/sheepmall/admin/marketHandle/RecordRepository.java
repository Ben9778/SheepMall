package com.blackfiresoft.sheepmall.admin.marketHandle;

import com.blackfiresoft.sheepmall.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Records, Long> {

    List<Records> findByActivityId(Long activityId);

    List<Records> findByProductNo(String productNo);

    List<Records> findByUsers(Users user);
}
