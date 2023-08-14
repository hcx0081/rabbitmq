package com.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * {@code @Description:} 消费者
 */
@Component
public class Recv {
    // 监听指定队列，接收一次消息就自动调用一次
    @RabbitListener(queues = {RabbitMQConfig.DIRECT_QUEUE})
    public void listenerQueue(Message message, Channel channel) throws InterruptedException {
        Thread.sleep(1000);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println(new String(message.getBody()));
            System.out.println("处理业务...");
            // int i = 1 / 0;// 出现业务异常
            // 手动确认
            // 参数：long deliveryTag, boolean multiple
            /*
             * deliveryTag：传递标识
             * multiple：true：接收所有消息包括传递标识    false：只接收传递标识
             *  */
            // channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            /* 发生异常时 */
            try {
                // 手动处理
                // 参数：long deliveryTag, boolean multiple, boolean requeue
                /*
                 * deliveryTag：传递标识
                 * multiple：true：拒绝所有消息包括传递标识    false：只拒绝传递标识
                 * requeue：true：返回拒绝的消息到队列，重新发送该消息给消费者   false：丢弃拒绝的消息
                 *  */
                channel.basicNack(deliveryTag, true, true);// 该方法针对多个消息
                // channel.basicReject(deliveryTag, true);// 该方法针对单个消息
            } catch (IOException ex) {
            
            }
        }
    }
}