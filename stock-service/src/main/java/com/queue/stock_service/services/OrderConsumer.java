package com.queue.stock_service.services;

import com.queue.common_service.dtos.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {


    Logger LOGGER=  LoggerFactory.getLogger(OrderConsumer.class);

    private NewTopic topic;
    KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderConsumer(NewTopic topic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "orders-topic",groupId ="${spring.kafka.consumer.group-id}" )
    public  void consume(OrderEvent orderEvent){
        LOGGER.info("order-created ->>>>>>>>>> : {}",orderEvent);
//        OrderEvent orderEvent=(OrderEvent) message.getPayload();
        Message<OrderEvent> message= MessageBuilder
                .withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC,topic.name())
                .build();
        kafkaTemplate.send(message);
        LOGGER.info("email sending is in process ------>>>>>>>>>> : {}",message);
    }


}
