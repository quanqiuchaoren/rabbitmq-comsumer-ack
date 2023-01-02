package com.qqcr.springboot.rabbitmq.consumer_ack.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class RabbitMqListener {
    @RabbitListener(queues = "consumerAckQueue_ack")
    public void ListenerQueue(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("consumerAckQueue_ack message:" + new String(message.getBody()));
        // 调用ack方法，手动确认消息已经收到，且消息被正常处理
        channel.basicAck(tag, false);
    }

    @RabbitListener(queues = "consumerAckQueue_nack_requeue")
    public void nackRequeueListener(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException, InterruptedException {
        System.out.println("consumerAckQueue_nack_requeue message: " + new String(message.getBody()));
        TimeUnit.SECONDS.sleep(2);
        // 收到消息，但是将消息进行nack处理，调用nack方法，切requeue参数设置为true，则消息会重新入队，还可以被消费，且会被标记为Unacked，在RabbitMQ的管理界面可以看到
        channel.basicNack(tag, false, true);
    }

    @RabbitListener(queues = "consumerAckQueue_nack_not_requeue")
    public void nackNotRequeueListener(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException, InterruptedException {
        System.out.println("consumerAckQueue_nack_not_requeue message: " + new String(message.getBody()));
        TimeUnit.SECONDS.sleep(2);
        /*
         * Reject one or several received messages.
         *
         * Supply the <code>deliveryTag</code> from the {@link com.rabbitmq.client.AMQP.Basic.GetOk}
         * or {@link com.rabbitmq.client.AMQP.Basic.GetOk} method containing the message to be rejected.
         * @see com.rabbitmq.client.AMQP.Basic.Nack
         * @param deliveryTag the tag from the received {@link com.rabbitmq.client.AMQP.Basic.GetOk} or {@link com.rabbitmq.client.AMQP.Basic.Deliver}
         * @param multiple true to reject all messages up to and including
         * the supplied delivery tag; false to reject just the supplied
         * delivery tag.
         * @param requeue true if the rejected message(s) should be requeued rather
         * than discarded/dead-lettered。如果为true，则重新入队，还可以被消费，如果为false，则重新入队，好像和死信队列有关。
         * @throws java.io.IOException if an error is encountered
         */
        // 收到消息，但是将消息进行nack处理，调用nack方法，切requeue参数设置为true，则消息会重新入队，且会被标记为Unacked，在RabbitMQ的管理界面可以看到
        channel.basicNack(tag, false, false);
    }
}
