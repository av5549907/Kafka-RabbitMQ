package com.queue.stock_service.services;

import com.queue.common_service.dtos.OrderEvent;
import com.queue.stock_service.constants.RabbitMQConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class RabbitMQOrderConsumer {
   RabbitTemplate rabbitTemplate;

    public RabbitMQOrderConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConstants.QUEUE)
    public  void consume(OrderEvent orderEvent){
        log.info("message consumed-----> {}",orderEvent.toString());
        if(orderEvent.getOrder().getTotalCost()>500000){
            throw new RuntimeException("Order creation failed");
        }
        log.info("order created------> {}",orderEvent.toString());
    }

}
