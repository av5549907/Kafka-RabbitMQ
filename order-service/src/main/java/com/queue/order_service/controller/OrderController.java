package com.queue.order_service.controller;

import com.queue.common_service.dtos.Order;
import com.queue.order_service.services.OrderProducer;
import com.queue.order_service.services.RabbitMQOrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class OrderController {
    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    RabbitMQOrderProducer rabbitMQOrderProducer;

    @GetMapping("/send-order")
    public ResponseEntity<String> createOrder(@RequestBody Order order){
        orderProducer.sendMessage(order);
        return ResponseEntity.ok("Order created");
    }

    @GetMapping("/publish")
    public ResponseEntity<String> sendOrder(@RequestBody Order order){
        rabbitMQOrderProducer.sendMessage(order);
        return ResponseEntity.ok("Order created");
    }
}
