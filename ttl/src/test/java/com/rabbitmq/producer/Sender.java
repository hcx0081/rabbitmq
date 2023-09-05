package com.rabbitmq.producer;

import com.rabbitmq.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
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
    void testSend() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, "", "hello", new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    // 设置队列中指定消息的有效期，单位为毫秒（expiration）
                    message.getMessageProperties().setExpiration("10000");
                    return message;
                }
            });
        }
    }
}