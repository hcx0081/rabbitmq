package com.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * {@code @description:} 生产者
 */
public class Sender {
    private static final String QUEUE_NAME = "confirm";
    private static final int MESSAGE_COUNT = 5000;
    
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // messagesIndividually();// 4950ms
        // messagesBatches();// 478ms
        messagesAsynchronously();// 192ms
    }
    
    private static void messagesIndividually() throws IOException, TimeoutException, InterruptedException {
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
        
        // 开启消息确认模式
        channel.confirmSelect();
        
        // 发送消息
        long start = System.currentTimeMillis();
        for (int i = 1; i <= MESSAGE_COUNT; i++) {
            String mes = "消息" + i;
            channel.basicPublish("", QUEUE_NAME, null, mes.getBytes(StandardCharsets.UTF_8));
            boolean flag = channel.waitForConfirms();
            if (flag) {
                System.out.println("消息发送成功");
            } else {
                System.out.println("消息发送失败");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");
        
        // 释放资源
        channel.close();
        connection.close();
    }
    
    private static void messagesBatches() throws IOException, TimeoutException, InterruptedException {
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
        
        // 开启消息确认模式
        channel.confirmSelect();
        
        // 发送消息
        int batchSize = 100;
        long start = System.currentTimeMillis();
        for (int i = 1; i <= MESSAGE_COUNT; i++) {
            String mes = "消息" + i;
            channel.basicPublish("", QUEUE_NAME, null, mes.getBytes(StandardCharsets.UTF_8));
            if (i % batchSize == 0) {
                boolean flag = channel.waitForConfirms();
                if (flag) {
                    System.out.println("消息发送成功");
                } else {
                    System.out.println("消息发送失败");
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");
        
        // 释放资源
        channel.close();
        connection.close();
    }
    
    private static void messagesAsynchronously() throws IOException, TimeoutException, InterruptedException {
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
        
        // 开启消息确认模式
        channel.confirmSelect();
        
        ConfirmCallback ackCallback = (deliveryTag, multiple) -> {
            System.out.println("消息确认成功");
            System.out.println("传递标识：" + deliveryTag);
            System.out.println("是否批量消息确认：" + multiple);
        };
        ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
            System.out.println("消息确认失败");
            System.out.println("传递标识：" + deliveryTag);
            System.out.println("是否批量消息确认：" + multiple);
        };
        channel.addConfirmListener(ackCallback, nackCallback);
        
        // 发送消息
        long start = System.currentTimeMillis();
        for (int i = 1; i <= MESSAGE_COUNT; i++) {
            String mes = "消息" + i;
            channel.basicPublish("", QUEUE_NAME, null, mes.getBytes(StandardCharsets.UTF_8));
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");
        
        // 释放资源
        channel.close();
        connection.close();
    }
}