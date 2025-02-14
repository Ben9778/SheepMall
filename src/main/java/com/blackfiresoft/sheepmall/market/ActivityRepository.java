package com.blackfiresoft.sheepmall.market;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByProductNo(String productNo);

    List<Activity> findByStatus(String status);
}
