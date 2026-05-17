package com.queue.order_service.services;

import com.queue.common_service.dtos.Order;
import com.queue.common_service.dtos.OrderEvent;
import com.queue.order_service.constants.RabbitMQConstants;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Service
public class RabbitMQOrderProducer {

    private RabbitTemplate rabbitTemplate;

    public RabbitMQOrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Order order){
        OrderEvent orderEvent=new OrderEvent();
        order.setOrderId(UUID.randomUUID().toString());
        orderEvent.setOrder(order);
        orderEvent.setMessage("");
        orderEvent.setStatus("Pending");
        log.info("message sent->>>>>>>>>>>>> {}",orderEvent.toString());
        rabbitTemplate.convertAndSend(RabbitMQConstants.exchange,RabbitMQConstants.routing_key,orderEvent);
    }
}
