package com.queue.stock_service.config;

import com.queue.stock_service.constants.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.retry.RetryTemplate;

import java.util.HashMap;

@Configuration
public class RabbitMQConfig {

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        // Define your native core retry configuration
        factory.setMessageConverter(
                jacksonJsonMessageConverter()
        );
        factory.setAdviceChain(
                RetryInterceptorBuilder.stateless()
                        .maxRetries(3)
                        .backOffOptions(
                                2000,1,100000
                        )
                        .recoverer(new RejectAndDontRequeueRecoverer())
                        .build()
        );

        return factory;
    }
    @Bean
    public Queue queue(){
        HashMap<String,Object> args=new HashMap<>();
        args.put("x-dead-letter-exchange", RabbitMQConstants.DLX);
        args.put("x-dead-letter-routing-key",RabbitMQConstants.DLQ_ROUTING_KEY);
        return new Queue(RabbitMQConstants.QUEUE,true,false,false,args);
    }

    @Bean
    public TopicExchange exchange(){
        return  new TopicExchange(RabbitMQConstants.EXCHANGE);
    }
    @Bean
    public Binding bind(){
        return BindingBuilder.bind(queue()).to(exchange()).with(RabbitMQConstants.ROUTING_KEY);
    }
    @Bean
    public Queue deadLaterQueue(){
        return new Queue(RabbitMQConstants.DLQ);
    }
    @Bean
    public TopicExchange deadLaterQueueExchange(){
       return new TopicExchange(RabbitMQConstants.DLX);
    }
    @Bean
    public Binding bindDeadLaterQueue(){
        return BindingBuilder.bind(deadLaterQueue()).to(deadLaterQueueExchange()).with(RabbitMQConstants.DLQ_ROUTING_KEY);
    }
    @Bean
    public JacksonJsonMessageConverter jacksonJsonMessageConverter(){
        return new JacksonJsonMessageConverter();
    }
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory factory){
        RabbitTemplate rabbitTemplate=new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(jacksonJsonMessageConverter());
        return rabbitTemplate;
    }

}
