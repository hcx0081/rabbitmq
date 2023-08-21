package com.rabbitmq.producer.ttl;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * {@code @Description:} 生产者
 */
public class Sender {
    private static final String NORMAL_EXCHANGE_NAME = "normal_exchange";
    private static final String DLX_EXCHANGE_NAME = "dlx_exchange";
    
    private static final String NORMAL_QUEUE_NAME = "normal_queue";
    private static final String DLX_QUEUE_NAME = "dlx_queue";
    
    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置参数
        // factory.setHost("192.168.106.100");// IP地址，默认为localhost
        factory.setPort(5672);// 端口号，默认为5672
        factory.setVirtualHost("/");// 虚拟机，默认为/
        // factory.setUsername("admin");// 用户名，默认为guest
        // factory.setPassword("admin");// 密码，默认为guest
        // 创建连接
        Connection connection = factory.newConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        
        // 创建正常交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE_NAME, BuiltinExchangeType.DIRECT, false, true, false, null);
        // 创建死信交换机
        channel.exchangeDeclare(DLX_EXCHANGE_NAME, BuiltinExchangeType.DIRECT, false, true, false, null);
        
        // 创建正常队列
        HashMap<String, Object> map = new HashMap<>();
        // map.put("x-message-ttl", 10000);// 设置队列中的所有消息的有效时长
        map.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);// 绑定死信交换机
        map.put("x-dead-letter-routing-key", "dlx-routing-key");// 指定死信路由键
        channel.queueDeclare(NORMAL_QUEUE_NAME, false, false, false, map);
        // 创建死信队列
        channel.queueDeclare(DLX_QUEUE_NAME, false, false, false, null);
        
        // 绑定正常队列到正常交换机
        channel.queueBind(NORMAL_QUEUE_NAME, NORMAL_EXCHANGE_NAME, "normal-routing-key");
        // 绑定死信队列到死信交换机
        channel.queueBind(DLX_QUEUE_NAME, DLX_EXCHANGE_NAME, "dlx-routing-key");
        
        // 发送消息
        AMQP.BasicProperties prop = new AMQP.BasicProperties().builder()
                                                              .expiration("10000")// 设置消息的有效时长
                                                              .build();
        for (int i = 1; i <= 10; i++) {
            String mes = "消息" + i;
            channel.basicPublish(NORMAL_EXCHANGE_NAME, "normal-routing-key", prop, mes.getBytes(StandardCharsets.UTF_8));
        }
        System.out.println("消息发送完毕");
        
        // 释放资源
        channel.close();
        connection.close();
    }
}