package com.blackfiresoft.sheepmall.mq;

import com.blackfiresoft.sheepmall.dto.ActivityDto;
import jakarta.annotation.Resource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MqProducer {

    @Resource
    private JmsTemplate jmsTemplate;

    public void sendMessage(ActivityDto activityDto) {

        jmsTemplate.convertAndSend("market", activityDto);
    }
}
