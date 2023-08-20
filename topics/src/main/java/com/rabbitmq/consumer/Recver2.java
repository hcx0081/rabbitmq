package com.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * {@code @Description:} 消费者
 */
public class Recver2 {
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
        
        // 创建队列
        // channel.queueDeclare(QUEUE_NAME_2, false, false, true, null);// 可以不用编写，因为生产者已经创建了该队列
        
        // 接收消息
        channel.basicConsume(QUEUE_NAME_2, true, new DefaultConsumer(channel) {
            // 回调方法，接收一次消息就自动调用一次
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // System.out.println("consumerTag：" + consumerTag);
                // System.out.println("exchange：" + envelope.getExchange());
                // System.out.println("routingKey：" + envelope.getRoutingKey());
                System.out.println("body：" + new String(body));
                System.out.println("存入数据库...");
            }
        });
        // 不要释放资源，而是需要一直等待接收消息
    }
}