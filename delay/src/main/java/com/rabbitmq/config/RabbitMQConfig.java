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
    public static final String TOPIC_EXCHANGE_NORMAL_NAME = "springboot_topic_exchange_normal";
    public static final String TOPIC_QUEUE_NORMAL_NAME = "springboot_topic_queue_normal";
    
    public static final String TOPIC_EXCHANGE_DLX_NAME = "springboot_topic_exchange_dlx";
    public static final String TOPIC_QUEUE_DLX_NAME = "springboot_topic_queue_dlx";
    
    // 创建正常交换机
    @Bean
    public Exchange normalExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE_NORMAL_NAME).durable(false).autoDelete().build();
    }
    
    // 创建正常队列
    @Bean
    public Queue normalQueue() {
        return QueueBuilder.nonDurable(TOPIC_QUEUE_NORMAL_NAME)
                           .autoDelete()
                           // .ttl(10000)// 设置队列中的所有消息的有效时长
                           .deadLetterExchange(TOPIC_EXCHANGE_DLX_NAME)// 绑定死信交换机
                           .deadLetterRoutingKey("repo.normal")// 指定死信路由键
                           .build();
    }
    
    // 创建死信交换机
    @Bean
    public Exchange dlxExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE_DLX_NAME).durable(false).autoDelete().build();
    }
    
    // 创建死信队列
    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.nonDurable(TOPIC_QUEUE_DLX_NAME).autoDelete().build();
    }
    
    // 绑定正常队列到正常交换机
    @Bean
    public Binding normalBinding(@Qualifier("normalQueue") Queue queue, @Qualifier("normalExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("order.#").noargs();
    }
    
    // 绑定正常队列到死信交换机
    @Bean
    public Binding normalAndDlxBinding(@Qualifier("normalQueue") Queue queue, @Qualifier("dlxExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("repo.#").noargs();
    }
    
    // 绑定死信队列到死信交换机
    @Bean
    public Binding dlxBinding(@Qualifier("dlxQueue") Queue queue, @Qualifier("dlxExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("repo.#").noargs();
    }
}