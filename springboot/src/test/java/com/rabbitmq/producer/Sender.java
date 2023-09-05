package com.rabbitmq.producer;

import com.rabbitmq.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code @description:} 生产者
 */
@SpringBootTest
class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Test
    void sendTest() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, "springboot.hello", "hello");
    }
}