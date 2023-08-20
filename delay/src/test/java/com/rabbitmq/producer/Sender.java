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
    void sendTest() throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NORMAL_NAME, "order.normal", "订单信息");
        // 倒计时结束模拟表示消息过期，消息将会成为死信，转发给死信交换机
        for (int i = 10; i > 0; i--) {
            System.out.println(i);
            Thread.sleep(1000);
        }
    }
    
    // 模拟程序运行，消费者监听队列接收消息
    @Test
    void go() {
        while (true) {
        
        }
    }
}