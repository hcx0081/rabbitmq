package com.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * {@code @Description:} 生产者
 */
public class Sender {
    private static final String QUEUE_NAME = "Ack Queues";
    
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
        
        // 创建队列
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
        
        // 发送消息
        Scanner scanner = new Scanner(System.in);
        String next;
        while (true) {
            next = scanner.nextLine();
            channel.basicPublish("", QUEUE_NAME, null, next.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送完毕");
        }
    }
}