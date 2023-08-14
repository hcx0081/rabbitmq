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
class Send {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    /**
     * 发送死信：
     * 1.消息过期
     * 2.队列长度限制
     * 3.消息拒收
     */
    @Test
    void testSend() {
        /* 测试消息过期 */
        // rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NORMAL, "normal.normal", "normal");
        
        /* 测试队列长度限制 */
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NORMAL, "normal.normal", "normal");
        }
    }
}