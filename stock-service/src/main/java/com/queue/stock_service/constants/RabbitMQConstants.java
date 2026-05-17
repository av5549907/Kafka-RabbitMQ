package com.queue.stock_service.constants;

public class RabbitMQConstants {
    private RabbitMQConstants(){}

    // MAIN
    public static final String EXCHANGE = "order_exchange";

    public static final String QUEUE = "order_queue";

    public static final String ROUTING_KEY = "order_routing_key";

    // DLQ
    public static final String DLQ = "order_dlq";

    public static final String DLX = "order_dlx";

    public static final String DLQ_ROUTING_KEY =
            "order_dlq_routing_key";
}
