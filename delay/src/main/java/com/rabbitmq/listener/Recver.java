package com.rabbitmq.listener;

import com.rabbitmq.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * {@code @description:} 消费者
 */
@Component
public class Recver {
    // 监听指定队列，接收一次消息就自动调用一次
    @RabbitListener(queues = {RabbitMQConfig.TOPIC_QUEUE_DLX_NAME})// 监听死信队列
    public void listenQueue(Message message) {
        System.out.println(new String(message.getBody()));
        System.out.println("根据订单信息查询订单状态...");
        // 订单信息1
        // 根据订单信息查询订单状态...
        // 订单信息2
        // 根据订单信息查询订单状态...
    }
}