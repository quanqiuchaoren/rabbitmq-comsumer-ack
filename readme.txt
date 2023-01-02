Consumer Ack模式概念：
1、让消息在RabbitMQ Broker（消息队列）与Consumer之间进行可靠性传递。
2、有三种模式：
    自动模式：自动确认收到消息。
    手动模式：消费者在收到消息后，手动确认收到消息，包括ack确认方式和nAck（not ack）确认方式。
    自动模式：根据异常类型进行确认，比较麻烦。


如果设置Consumer Ack模式：
1、可以再yml配置文件中设置