package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code @Description:} RabbitMQ配置类
 */
@Configuration
public class RabbitMQConfig {
    public static final String X_EXCHANGE_DELAYED_NAME = "springboot_x_exchange_delayed";
    public static final String X_QUEUE_DELAYED_NAME = "springboot_x_queue_delayed";
    
    // 创建延迟交换机
    @Bean
    public Exchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");// 1.设置延迟交换机的类型
        // 2.创建延迟交换机
        return new CustomExchange(X_EXCHANGE_DELAYED_NAME, "x-delayed-message", true, false, args);
    }
    
    // 创建正常队列
    @Bean
    public Queue delayedQueue() {
        return QueueBuilder.nonDurable(X_QUEUE_DELAYED_NAME).build();
    }
    
    // 绑定正常队列到延迟交换机
    @Bean
    public Binding delayedBinding(@Qualifier("delayedQueue") Queue queue, @Qualifier("delayedExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("repo.info").noargs();
    }
}