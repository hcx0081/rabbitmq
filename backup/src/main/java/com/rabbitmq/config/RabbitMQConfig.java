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
    public static final String NORMAL_EXCHANGE_NAME = "springboot_normal_exchange";
    public static final String BACKUP_EXCHANGE_NAME = "springboot_backup_exchange";
    
    public static final String NORMAL_QUEUE_NAME = "springboot_normal_queue";
    public static final String BACKUP_QUEUE_NAME = "springboot_backup_queue";
    
    // 创建正常交换机
    @Bean
    public Exchange normalExchange() {
        return ExchangeBuilder.directExchange(NORMAL_EXCHANGE_NAME)
                              .durable(false)
                              .autoDelete()
                              .withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME)// 配置备份交换机
                              .build();
    }
    
    // 创建备份交换机
    @Bean
    public Exchange backupExchange() {
        return ExchangeBuilder.fanoutExchange(BACKUP_EXCHANGE_NAME).durable(false).autoDelete().build();
    }
    
    // 创建正常队列
    @Bean
    public Queue normalQueue() {
        return QueueBuilder.nonDurable(NORMAL_QUEUE_NAME).autoDelete().build();
    }
    
    // 创建备份队列
    @Bean
    public Queue backupQueue() {
        return QueueBuilder.nonDurable(BACKUP_QUEUE_NAME).autoDelete().build();
    }
    
    // 绑定正常队列到正常交换机
    @Bean
    public Binding normalBinding(@Qualifier("normalQueue") Queue queue, @Qualifier("normalExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("normal").noargs();
    }
    
    // 绑定备份队列到备份交换机
    @Bean
    public Binding backupBinding(@Qualifier("backupQueue") Queue queue, @Qualifier("backupExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }
}