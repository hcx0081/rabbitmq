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
    public static final String DIRECT_EXCHANGE = "springboot_direct_exchange";
    public static final String DIRECT_QUEUE = "springboot_direct_queue";
    
    // 创建交换机
    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(DIRECT_EXCHANGE).durable(false).build();
    }
    
    // 创建队列
    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable(DIRECT_QUEUE).build();
    }
    
    // 绑定队列到交换机
    @Bean
    public Binding binding(@Qualifier("queue") Queue queue, @Qualifier("exchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }
}