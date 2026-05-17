package com.queue.order_service.services;

import com.queue.common_service.dtos.Order;
import com.queue.common_service.dtos.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class OrderProducer {



    private NewTopic topic;

    Logger LOGGER=  LoggerFactory.getLogger(OrderProducer.class);
    KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderProducer(NewTopic topic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Order order){
        OrderEvent orderEvent=new OrderEvent();
        order.setOrderId(UUID.randomUUID().toString());
        orderEvent.setOrder(order);
        orderEvent.setMessage("order creation in pending");
        orderEvent.setStatus("In process");
        LOGGER.info("order creation in pending-->{}",orderEvent.toString());
        Message<OrderEvent> message= MessageBuilder
                .withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC,topic.name())
                .build();
        kafkaTemplate.send(message);
    }
}
