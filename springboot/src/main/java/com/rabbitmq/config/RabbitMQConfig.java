package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code @description:} RabbitMQ配置类
 */
@Configuration
public class RabbitMQConfig {
    public static final String TOPIC_EXCHANGE_NAME = "springboot_topic_exchange";
    public static final String TOPIC_QUEUE_NAME = "springboot_topic_queue";
    
    // 创建交换机
    @Bean
    public Exchange topicExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE_NAME).durable(false).autoDelete().build();
    }
    
    // 创建队列
    @Bean
    public Queue topicQueue() {
        return QueueBuilder.nonDurable(TOPIC_QUEUE_NAME).autoDelete().build();
    }
    
    // 绑定队列到交换机
    @Bean
    public Binding topicBinding(@Qualifier("topicQueue") Queue queue, @Qualifier("topicExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("springboot.#").noargs();
    }
}