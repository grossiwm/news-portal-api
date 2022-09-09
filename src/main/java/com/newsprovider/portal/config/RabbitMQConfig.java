package com.newsprovider.portal.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Autowired
    ConnectionFactory connectionFactory;

    @Value("${spring.rabbitmq.queue.payments.toProcess.name}")
    private String paymentsToProcessQueueName;

    @Value("${spring.rabbitmq.queue.payments.processed.name}")
    private String paymentsProcessedQueueName;

    @Value("${spring.rabbitmq.queue.subscriptionNotification.name}")
    private String subscriptionNotificationQueueName;

    @Value("${spring.rabbitmq.exchange.payments.name}")
    private String paymentsExchangeName;

    @Value("${spring.rabbitmq.exchange.subscriptionNotification.name}")
    private String subscriptionNotificationExchangeName;

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
    Queue subscriptionNotificationQueue(AmqpAdmin amqpAdmin) {
        Queue queue = new Queue(subscriptionNotificationQueueName, true);
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
    DirectExchange subscriptionNotificationExchange(AmqpAdmin amqpAdmin) {
        DirectExchange directExchange = new DirectExchange(subscriptionNotificationExchangeName);
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

    @Bean
    Binding subscriptionNotificationBinding(Queue subscriptionNotificationQueue, DirectExchange subscriptionNotificationExchange, AmqpAdmin amqpAdmin) {
        Binding binding = BindingBuilder.bind(subscriptionNotificationQueue).to(subscriptionNotificationExchange)
                .withQueueName();
        amqpAdmin.declareBinding(binding);
        return binding;
    }

}
