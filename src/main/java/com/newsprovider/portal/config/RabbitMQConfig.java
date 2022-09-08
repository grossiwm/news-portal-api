package com.newsprovider.portal.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Autowired
    ConnectionFactory connectionFactory;

    @Value("${spring.rabbitmq.queue.payments.toProcess.name}")
    private String paymentsToProcessQueueName;

    @Value("${spring.rabbitmq.queue.payments.processed.name}")
    private String paymentsProcessedQueueName;

    @Value("${spring.rabbitmq.exchange.payments.name}")
    private String paymentsExchangeName;

    @Bean
    AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    Queue paymentsToProcessQueue(AmqpAdmin amqpAdmin) {
        Queue queue = new Queue(paymentsToProcessQueueName, true);
        amqpAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Queue paymentsProcessedQueue(AmqpAdmin amqpAdmin) {
        Queue queue = new Queue(paymentsProcessedQueueName, true);
        amqpAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    DirectExchange directExchange(AmqpAdmin amqpAdmin) {
        DirectExchange directExchange = new DirectExchange(paymentsExchangeName);
        amqpAdmin.declareExchange(directExchange);
        return directExchange;
    }

    @Bean
    Binding paymentsToProcessBinding(Queue paymentsToProcessQueue, DirectExchange directExchange, AmqpAdmin amqpAdmin) {
        Binding binding = BindingBuilder.bind(paymentsToProcessQueue).to(directExchange)
                .withQueueName();
        amqpAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    Binding paymentsProcessedBinding(Queue paymentsProcessedQueue, DirectExchange directExchange, AmqpAdmin amqpAdmin) {
        Binding binding = BindingBuilder.bind(paymentsProcessedQueue).to(directExchange)
                .withQueueName();
        amqpAdmin.declareBinding(binding);
        return binding;
    }
}
