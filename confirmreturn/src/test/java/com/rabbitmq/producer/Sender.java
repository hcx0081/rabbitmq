package com.rabbitmq.producer;

import com.rabbitmq.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Test
    void testConfirm() {
        // 定义回调方法
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * @param correlationData 相关配置信息
             * @param ack 交换机是否成功接收到生产者发布的消息
             * @param cause 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("执行confirm()方法...");
                if (ack) {
                    System.out.println("成功接收雄安锡：" + cause);
                } else {
                    System.out.println("接收失败：" + cause);
                    /* 进行处理，让消息再次发送 */
                }
            }
        });
        // 发送消息
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, "", "confirm");
    }
    
    @Test
    void testReturn() {
        
        /* 设置交换机处理失败消息的模式 */
        rabbitTemplate.setMandatory(true);// 新版本不开启也可以执行下面的回调方法
        
        // 定义回调方法
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            /**
             * @param returned 返回的消息
             */
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                System.out.println("执行returnedMessage()方法...");
                System.out.println(new String(returned.getMessage().getBody()));
                System.out.println("交换机：" + returned.getExchange());
                System.out.println("路由键：" + returned.getRoutingKey());
                System.out.println("错误码：" + returned.getReplyCode());
                System.out.println("错误信息：" + returned.getReplyText());
            }
        });
        // 发送消息，发送错误的路由键
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, "这是错误的路由键", "return");
    }
}
