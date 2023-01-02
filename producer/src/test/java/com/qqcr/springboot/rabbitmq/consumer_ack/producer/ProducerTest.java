package com.qqcr.springboot.rabbitmq.consumer_ack.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {
    //1.注入RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void send_message_to_ack_queue(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"consumerAckKey.ack","consumer ack mq hello~~~");
    }

    @Test
    public void send_message_to_not_ack_requeue(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"consumerAckKey.nack.requeue","consumer nack mq requeue hello~~~");
    }

    @Test
    public void send_message_to_not_ack_not_requeue(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"consumerAckKey.nack.not.requeue","consumer nack mq not requeue hello~~~");
    }
}
