package com.queue.order_service.constants;

import org.apache.kafka.common.protocol.types.Field;

public class RabbitMQConstants {
    public static final String queue_name= "producer_queue";
    public static final String routing_key="order_routing_key";
    public static final String exchange="order_exchange";

}
