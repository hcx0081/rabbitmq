package com.rabbitmq.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description: 生产者
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-rabbitmq-producer.xml")
public class Send {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Test
    public void testHelloWorld() {
        rabbitTemplate.convertAndSend("spring_default_queue", "hello");
    }
    
    @Test
    public void testPubSub() {
        rabbitTemplate.convertAndSend("spring_fanout_exchange", "", "hello");
    }
    
    @Test
    public void testTopics() {
        rabbitTemplate.convertAndSend("spring_topic_exchange", "order.info", "order.info");
        rabbitTemplate.convertAndSend("spring_topic_exchange", "user.error", "user.error");
    }
}
