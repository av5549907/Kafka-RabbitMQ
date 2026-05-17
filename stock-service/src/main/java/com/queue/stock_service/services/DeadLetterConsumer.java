package com.queue.stock_service.services;

import com.queue.common_service.dtos.OrderEvent;
import com.queue.stock_service.constants.RabbitMQConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class DeadLetterConsumer {

    @RabbitListener(queues = RabbitMQConstants.DLQ)
    public void consumeDeadLaterMessage(OrderEvent orderEvent){
        log.info("message sent to DLQ-----> {}",orderEvent.toString());
    }
}
