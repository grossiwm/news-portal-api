package com.newsprovider.portal.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Autowired
    ConnectionFactory connectionFactory;

    @Bean
    AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    Queue paymentsToProcessQueue(AmqpAdmin amqpAdmin) {
        Queue queue = new Queue("paymentsToProcess", true);
        amqpAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Queue paymentsProcessedQueue(AmqpAdmin amqpAdmin) {
        Queue queue = new Queue("paymentsProcessed", true);
        amqpAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    DirectExchange directExchange(AmqpAdmin amqpAdmin) {
        DirectExchange directExchange = new DirectExchange("direct-exchange");
        amqpAdmin.declareExchange(directExchange);
        return directExchange;
    }

    @Bean
    Binding paymentsToProcessBinding(Queue paymentsToProcessQueue, DirectExchange directExchange, AmqpAdmin amqpAdmin) {
        Binding binding = BindingBuilder.bind(paymentsToProcessQueue).to(directExchange)
                .with("paymentsToProcess");
        amqpAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    Binding paymentsProcessedBinding(Queue paymentsProcessedQueue, DirectExchange directExchange, AmqpAdmin amqpAdmin) {
        Binding binding = BindingBuilder.bind(paymentsProcessedQueue).to(directExchange)
                .with("paymentsProcessed");
        amqpAdmin.declareBinding(binding);
        return binding;
    }
}
