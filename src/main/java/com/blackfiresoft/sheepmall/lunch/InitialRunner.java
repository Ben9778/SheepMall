package com.blackfiresoft.sheepmall.lunch;

import com.blackfiresoft.sheepmall.payment.InitPayProperty;
import com.blackfiresoft.sheepmall.payment.PayRqsRepository;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化加载支付配置参数
 */
@Component
public class InitialRunner implements ApplicationRunner {
    @Resource
    private PayRqsRepository payRqsRepository;

    @Override
    public void run(ApplicationArguments args) {
        // 初始化支付配置参数
        payRqsRepository.findById(1L).ifPresent(InitPayProperty::setPayProperty);
    }
}
