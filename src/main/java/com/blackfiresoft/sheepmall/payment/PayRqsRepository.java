package com.blackfiresoft.sheepmall.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRqsRepository extends JpaRepository<PayRqs, Long> {
}
