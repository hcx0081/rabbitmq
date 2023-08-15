package com.rabbitmq.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * {@code @Description:} 消费者
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-rabbitmq-consumer.xml")
public class Recver {
    // 接收消息
    @Test
    public void testReceive() {
        // while (true) {
        //
        // }
    }
}