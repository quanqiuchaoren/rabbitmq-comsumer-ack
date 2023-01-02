package com.qqcr.springboot.rabbitmq.consumer_ack.producer;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "boot_topic_exchange";
    public static final String CONSUMER_ACK_QUEUE_ACK = "consumerAckQueue_ack";

    public static final String CONSUMER_ACK_QUEUE_NACK_REQUEUE = "consumerAckQueue_nack_requeue";

    public static final String CONSUMER_ACK_QUEUE_NACK_NOT_REQUEUE = "consumerAckQueue_nack_not_requeue";

    //1.交换机
    @Bean("consumerAckExchange")
    public Exchange bootExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }


    //2.Queue 队列
    @Bean("consumerAckQueue_ack")
    public Queue consumerAckQueue_ack() {
        return QueueBuilder.durable(CONSUMER_ACK_QUEUE_ACK).build();
    }

    //2.Queue 队列
    @Bean("consumerAckQueue_nack_requeue")
    public Queue consumerAckQueue_nack_requeue() {
        return QueueBuilder.durable(CONSUMER_ACK_QUEUE_NACK_REQUEUE).build();
    }

    @Bean("consumerAckQueue_nack_not_requeue")
    public Queue consumerAckQueue_nack_not_requeue() {
        return QueueBuilder.durable(CONSUMER_ACK_QUEUE_NACK_NOT_REQUEUE).build();
    }

    //3. 队列和交互机绑定关系 Binding
    /*
        1. 知道哪个队列
        2. 知道哪个交换机
        3. routing key
     */
    @Bean
    public Binding bindAck(@Qualifier("consumerAckQueue_ack") Queue queue, @Qualifier("consumerAckExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("consumerAckKey.ack").noargs();
    }

    @Bean
    public Binding bindNackRequeue(@Qualifier("consumerAckQueue_nack_requeue") Queue queue, @Qualifier("consumerAckExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("consumerAckKey.nack.requeue").noargs();
    }

    @Bean
    public Binding bindNackNotRequeue(@Qualifier("consumerAckQueue_nack_not_requeue") Queue queue, @Qualifier("consumerAckExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("consumerAckKey.nack.not.requeue").noargs();
    }
}
