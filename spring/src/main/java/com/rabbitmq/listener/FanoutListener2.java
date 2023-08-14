package com.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * {@code @Description:}
 */
public class FanoutListener2 implements MessageListener {
    // 接收一次消息就自动调用一次
    @Override
    public void onMessage(Message message) {
        System.out.println(new String(message.getBody()));
    }
}