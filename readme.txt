Consumer Ack模式概念：
1、让消息在RabbitMQ Broker（消息队列）与Consumer之间进行可靠性传递。
2、有三种模式：
    自动模式：自动确认收到消息。
    手动模式：消费者在收到消息后，手动确认收到消息，包括ack确认方式和nAck（not ack）确认方式。
    自动模式：根据异常类型进行确认，比较麻烦。


如果设置Consumer Ack模式：
1、可以在消费者端的yml配置文件中设置spring.rabbitmq.listener.simple.acknowledge-mode的值，
    默认为none，
    manual代表手动确认，
    auto代表使用抛出的异常来进行确认。
    需要注意的是，还有一个设置项的名字为spring.rabbitmq.listener.direct.acknowledge-mode，设置了不起作用。
2、使用channel.basicAck来代表确认收到消息，使用channel.basicNack来代表出异常了，需要将消息标记为Unacked。