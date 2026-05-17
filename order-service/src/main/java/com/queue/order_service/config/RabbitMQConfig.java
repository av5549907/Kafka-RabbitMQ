package com.queue.order_service.config;

import com.queue.order_service.constants.RabbitMQConstants;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
   public JacksonJsonMessageConverter messageConverter(){
       return new JacksonJsonMessageConverter();
   }
   @Bean
   public TopicExchange exchange(){
        return new TopicExchange(RabbitMQConstants.exchange);
   }

   @Bean
   public AmqpTemplate template(ConnectionFactory factory){
       RabbitTemplate rabbitTemplate=new RabbitTemplate(factory);
       rabbitTemplate.setMessageConverter(messageConverter());
       return rabbitTemplate;
   }
}
