package com.rabbitmq;

import com.rabbitmq.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Test
    void sendTest() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.NORMAL_EXCHANGE_NAME, "", "消息1", message -> {
            MessageProperties prop = message.getMessageProperties();
            // 发送优先级为1级的消息
            prop.setPriority(1);
            return message;
        });
        rabbitTemplate.convertAndSend(RabbitMQConfig.NORMAL_EXCHANGE_NAME, "", "消息2", message -> {
            MessageProperties prop = message.getMessageProperties();
            // 发送优先级为10级的消息
            prop.setPriority(10);
            return message;
        });
    }
}