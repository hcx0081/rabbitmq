package com.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description: 消费者
 */
public class Recv1 {
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
        String queueName = "Work queues";
        channel.queueDeclare(queueName, false, false, true, null);// 可以不用编写，因为生产者已经创建了该队列
        
        // 接收消息
        // 参数：String queue, boolean autoAck, Consumer callback
        /*
         * queue：队列名称
         * autoAck：是否自动确认消息
         * callback：回调对象
         *  */
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            // 回调方法，接收一次消息就自动调用一次
            /*
             * consumerTag：消息者标识
             * envelope：虚拟机中的信息
             * properties：配置信息
             * body：消息数据
             *  */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // System.out.println("consumerTag：" + consumerTag);
                // System.out.println("exchange：" + envelope.getExchange());
                // System.out.println("routingKey：" + envelope.getRoutingKey());// Work queues
                System.out.println("body：" + new String(body));
            }
        });
        // 不要释放资源，而是需要一直等待接收消息
    }
}
