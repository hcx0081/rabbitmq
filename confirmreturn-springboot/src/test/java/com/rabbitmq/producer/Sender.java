package com.rabbitmq.producer;

import com.rabbitmq.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Test
    void confirmTest() {
        // 定义回调方法
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("执行confirm()方法...");
                System.out.println(correlationData.getId());
                if (ack) {
                    System.out.println("发送成功：" + cause);
                } else {
                    System.out.println("发送失败：" + cause);
                    /* 进行处理，再次发送消息 */
                }
            }
        });
        // 发送消息
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE_NAME, "这是正确的路由键", "confirm", new CorrelationData("id"));
        // rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE_NAME + "这是错误的交换机", "这是正确的路由键", "confirm", new CorrelationData("id"));
    }
    
    @Test
    void returnTest() {
        // 定义回调方法
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                System.out.println("执行returnedMessage()方法...");
                System.out.println(new String(returned.getMessage().getBody()));
                System.out.println("交换机：" + returned.getExchange());
                System.out.println("路由键：" + returned.getRoutingKey());
                System.out.println("错误码：" + returned.getReplyCode());
                System.out.println("错误信息：" + returned.getReplyText());
            }
        });
        // 发送消息，指定错误的路由键
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE_NAME, "这是错误的路由键", "return");
    }
}