package com.rabbitmq;

import com.rabbitmq.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Test
    void sendTest() {
        // 发送消息
        rabbitTemplate.convertAndSend(RabbitMQConfig.NORMAL_EXCHANGE_NAME, "这是错误的路由键", "消息");
    }
}