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
    public static final String TOPIC_EXCHANGE_NORMAL = "springboot_topic_exchange_normal";
    public static final String TOPIC_QUEUE_NORMAL = "springboot_topic_queue_normal";
    public static final String TOPIC_EXCHANGE_DLX = "springboot_topic_exchange_dlx";
    public static final String TOPIC_QUEUE_DLX = "springboot_topic_queue_dlx";
    
    // 创建正常交换机
    @Bean
    public Exchange normalExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE_NORMAL).durable(false).build();
    }
    // 创建正常队列
    @Bean
    public Queue normalQueue() {
        return QueueBuilder.nonDurable(TOPIC_QUEUE_NORMAL)
                .ttl(10000)
                // 设置死信交换机（x-dead-letter-exchange）
                .deadLetterExchange(TOPIC_EXCHANGE_DLX)
                // 设置死信路由键（x-dead-letter-routing-key），当消息死信后以该路由键发送给死信交换机
                .deadLetterRoutingKey("repo.normal")
                .build();
    }
    
    // 创建死信交换机
    @Bean
    public Exchange dlxExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE_DLX).durable(false).build();
    }
    // 创建死信队列
    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.nonDurable(TOPIC_QUEUE_DLX).build();
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