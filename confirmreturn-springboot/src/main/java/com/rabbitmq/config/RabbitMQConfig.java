package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code @Description:} RabbitMQ配置类
 */
@Configuration
public class RabbitMQConfig {
    public static final String DIRECT_EXCHANGE_NAME = "springboot_direct_exchange";
    public static final String DIRECT_QUEUE_NAME = "springboot_direct_queue";
    
    // 创建交换机
    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(DIRECT_EXCHANGE_NAME).durable(false).autoDelete().build();
    }
    
    // 创建队列
    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable(DIRECT_QUEUE_NAME).autoDelete().build();
    }
    
    // 绑定队列到交换机
    @Bean
    public Binding binding(@Qualifier("queue") Queue queue, @Qualifier("exchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("这是正确的路由键").noargs();
    }
}