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
    public static final String DIRECT_EXCHANGE = "springboot_direct_exchange_ttl";
    public static final String DIRECT_QUEUE = "springboot_direct_queue_ttl";
    
    // 创建交换机
    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(DIRECT_EXCHANGE).durable(false).autoDelete().build();
    }
    
    // 创建队列
    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable(DIRECT_QUEUE)
                           .autoDelete()
                           // 设置队列中所有消息的有效期，单位为毫秒（x-message-ttl）
                           .ttl(100000)
                           // 设置队列的有效期，单位为毫秒（x-expires）
                           .expires(50000)
                           .build();
    }
    
    // 绑定队列到交换机
    @Bean
    public Binding binding(@Qualifier("queue") Queue queue, @Qualifier("exchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }
}