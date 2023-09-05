package com.rabbitmq.producer;

import com.rabbitmq.config.RabbitMQConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessageProperties;
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
    
    @BeforeEach
    @Test
    void sendTest() throws InterruptedException {
        // rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NORMAL_NAME, "order.normal", "订单信息");
        // 优化
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NORMAL_NAME, "order.normal", "订单信息1", message -> {
            MessageProperties prop = message.getMessageProperties();
            prop.setExpiration("10000");// 设置消息的有效时长
            return message;
        });
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NORMAL_NAME, "order.normal", "订单信息2", message -> {
            MessageProperties prop = message.getMessageProperties();
            prop.setExpiration("1000");// 设置消息的有效时长
            return message;
        });
    }
    
    // 模拟程序运行，消费者监听队列接收消息
    @Test
    void go() {
        while (true) {
        
        }
    }
}