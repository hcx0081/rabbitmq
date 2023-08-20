package com.rabbitmq;

import com.rabbitmq.config.RabbitMQConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @BeforeEach
    @Test
    void sendTest() {
        // 基于插件
        rabbitTemplate.convertAndSend(RabbitMQConfig.X_EXCHANGE_DELAYED_NAME, "order.info", "订单信息1", message -> {
            MessageProperties prop = message.getMessageProperties();
            prop.setDelay(10000);// 3.设置消息的延迟时长
            return message;
        });
        rabbitTemplate.convertAndSend(RabbitMQConfig.X_EXCHANGE_DELAYED_NAME, "order.info", "订单信息2", message -> {
            MessageProperties prop = message.getMessageProperties();
            prop.setDelay(1000);// 3.设置消息的延迟时长
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