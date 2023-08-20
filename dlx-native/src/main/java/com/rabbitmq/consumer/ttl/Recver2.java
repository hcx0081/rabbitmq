package com.rabbitmq.consumer.ttl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * {@code @Description:} 消费者
 */
public class Recver2 {
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
        
        // 创建队列
        // channel.queueDeclare(DLX_QUEUE_NAME, false, false, true, null);// 可以不用编写，因为生产者已经创建了该队列
        
        // 接收死信队列的消息
        channel.basicConsume(DLX_QUEUE_NAME, true, "消费者2标识",
                (consumerTag, message) -> {
                    System.out.println("传递标识：" + message.getEnvelope().getDeliveryTag() + "\n" + "接收的消息：" + new String(message.getBody()));
                },
                (consumerTag) -> {});
        // 不要释放资源，而是需要一直等待接收消息
    }
}