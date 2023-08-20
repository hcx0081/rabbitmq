package com.rabbitmq.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * {@code @Description:} 生产者
 */
public class Sender {
    private static final String EXCHANGE_NAME = "Topic Exchange";
    
    private static final String QUEUE_NAME_1 = "Topics1";
    private static final String QUEUE_NAME_2 = "Topics2";
    
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
        
        // 创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC, false, false, false, null);
        
        // 创建队列
        channel.queueDeclare(QUEUE_NAME_1, false, false, true, null);
        channel.queueDeclare(QUEUE_NAME_2, false, false, true, null);
        
        // 绑定队列到交换机
        // 路由键：系统.级别
        // 所有系统和所有级别的日志存入日志文件
        channel.queueBind(QUEUE_NAME_1, EXCHANGE_NAME, "*.*");
        // 所有order系统的日志存入数据库，所有error级别的日志存入数据库
        channel.queueBind(QUEUE_NAME_2, EXCHANGE_NAME, "order.#");
        channel.queueBind(QUEUE_NAME_2, EXCHANGE_NAME, "#.error");
        
        // 发送消息
        channel.basicPublish(EXCHANGE_NAME, "order.info", null, "订单系统提示".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(EXCHANGE_NAME, "user.error", null, "用户系统错误".getBytes(StandardCharsets.UTF_8));
        
        // 释放资源
        channel.close();
        connection.close();
    }
}