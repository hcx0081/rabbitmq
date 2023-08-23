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
    public static final String NORMAL_EXCHANGE_NAME = "springboot_normal_exchange";
    
    public static final String PRIORITY_QUEUE_NAME = "springboot_priority_queue";
    
    // 创建交换机
    @Bean
    public Exchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange(NORMAL_EXCHANGE_NAME).durable(false).autoDelete().build();
    }
    
    // 创建优先队列
    @Bean
    public Queue priorityQueue() {
        return QueueBuilder.nonDurable(PRIORITY_QUEUE_NAME)
                           .autoDelete()
                           .withArgument("x-max-priority", 10)
                           .build();
    }
    
    // 绑定优先队列到交换机
    @Bean
    public Binding priorityBinding(@Qualifier("priorityQueue") Queue queue, @Qualifier("fanoutExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }
}