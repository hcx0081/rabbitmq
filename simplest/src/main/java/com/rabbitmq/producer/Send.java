package com.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Description: 生产者
 */
public class Send {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置参数
        factory.setHost("192.168.106.100");// IP地址，默认为localhost
        factory.setPort(5672);// 端口号，默认为5672
        factory.setVirtualHost("/");// 虚拟机，默认为/
        factory.setUsername("admin");// 用户名，默认为guest
        factory.setPassword("admin");// 密码，默认为guest
        // 创建连接
        Connection connection = factory.newConnection();
        // 创建Channel
        Channel channel = connection.createChannel();
        // 创建队列
        // 参数：String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        /*
         * queue：队列名称
         * durable：是否消息持久化
         * exclusive：是否排他连接，即只能在同一连接中访问该队列，
         *            只有首次声明它的连接可以访问，其他连接不可以访问，并在该连接断开时自动删除，无论消息是否持久化
         * autoDelete：队列不再使用时是否自动删除，自动删除的前提为：至少有一个消费者连接到该队列，没有消费者连接则不会自动删除
         * arguments：参数，可以设置队列的其他参数，例如设置存活时间
         */
        String queueName = "Hello World";
        channel.queueDeclare(queueName, false, false, true, null);
        
        // 发送消息
        // 参数：String exchange, String routingKey, BasicProperties props, byte[] body
        /*
         * exchange：交换机名称，如果设置为""将使用RabbitMQ的默认交换机
         * routingKey：路由键，交换机会将消息分发到与其绑定的符合指定路由键的队列
         * props：消息属性
         * body：消息内容
         *  */
        channel.basicPublish("", queueName, null, "hello".getBytes(StandardCharsets.UTF_8));
        // 释放资源
        channel.close();
        connection.close();
    }
}
