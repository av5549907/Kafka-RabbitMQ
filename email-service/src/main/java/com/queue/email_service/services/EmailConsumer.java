package com.queue.email_service.services;

import com.queue.common_service.dtos.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {
    Logger LOGGER=  LoggerFactory.getLogger(EmailConsumer.class);

    @KafkaListener(topics = "stock-topic",groupId = "${spring.kafka.consumer.group-id}" )
    public void consume(Message message){
      LOGGER.info("message---->>>>>: {}",message.getPayload().toString());

    }
}
