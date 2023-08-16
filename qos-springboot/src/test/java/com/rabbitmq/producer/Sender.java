package com.rabbitmq.producer;

import com.rabbitmq.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code @Description:} 生产者
 */
@SpringBootTest
class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Test
    void testSend() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, "", "hello");
        }
    }
    
    /**
     * 模拟程序运行，消费者监听队列接收消息
     */
    @Test
    void go() {
        while (true) {
        
        }
    }
}