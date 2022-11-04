package com.rabbitmq.producer;

import com.rabbitmq.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: 生产者
 */
@SpringBootTest
class Send {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Test
    void testSend() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, "", "hello");
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
